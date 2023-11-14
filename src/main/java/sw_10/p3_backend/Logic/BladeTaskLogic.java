package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BladeTaskLogic {

    private final BladeTaskRepository bladeTaskRepository;
    private final BladeProjectRepository bladeProjectRepository;
    private final BookingLogic bookingLogic;


    @Autowired
    public BladeTaskLogic(BladeTaskRepository bladeTaskRepository, BladeProjectRepository bladeProjectRepository
    , BookingLogic bookingLogic) {
        this.bladeTaskRepository = bladeTaskRepository;
        this.bladeProjectRepository = bladeProjectRepository;
        this.bookingLogic = bookingLogic;
    }


    public String deleteTask(Integer id){
        try {
            bladeTaskRepository.deleteById(id.longValue());
            return "BT deleted";
        }
        catch (Exception e) {
            return "Error deleting BT" + e;
        }
    }

    public BladeTask createBladeTask(BladeTaskInput input) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        validateBladeTaskInput(input);
        
        // Find the blade project in the database
        BladeProject bladeProject = bladeProjectRepository.findById((long) input.bladeProjectId())
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + input.bladeProjectId()));

        LocalDate endDate = calculateEndDate(input);
        
        
        //Set testrig to 0 if none is provided
        int testRigValue = Optional.ofNullable(input.testRig()).orElse(0);

        // Create a new BladeTask instance
        BladeTask newBladeTask = new BladeTask(
                input.startDate(),
                endDate,
                input.duration(),
                input.testType(),
                input.attachPeriod(),
                input.detachPeriod(),
                input.taskName(),
                testRigValue,
                bladeProject
        );


        // Create a new ResourceOrder for each ResourceOrderInput in the input
        if(input.resourceOrders() != null) {
            for (ResourceOrderInput resourceOrderInput : input.resourceOrders()) {
                // Create a new ResourceOrder instance
                ResourceOrder resourceOrder = new ResourceOrder(
                        resourceOrderInput.type(),
                        resourceOrderInput.amount(),
                        resourceOrderInput.equipmentAssignmentStatus(),
                        resourceOrderInput.workHours(),
                        newBladeTask);

                // Save the new ResourceOrder in the database
                newBladeTask.addResourceOrder(resourceOrder);

            }
        }
        List<ResourceOrder> resourceOrders = newBladeTask.getResourceOrders();
        if(testRigValue != 0){
            bookingLogic.createBookings(resourceOrders);
        }
        // Save the new BladeTask in the database
        bladeTaskRepository.save(newBladeTask);

        // Return the new BladeTask
        return newBladeTask;
    }

    public List<BladeTask> findAll(){
        return bladeTaskRepository.findAll();
    }

    public BladeTask findOne(Integer id) throws NotFoundException {
        try {
            Optional<BladeTask> bladeTask = bladeTaskRepository.findById(Long.valueOf(id));
            if (bladeTask.isEmpty())
                throw new NotFoundException("BladeTask not found with id: " + id);
            return bladeTask.get();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error getting BladeTask", e);
        }
    }

    private void validateBladeTaskInput(BladeTaskInput input){
        if (input.duration() == null) {
            throw new InputInvalidException("duration is mandatory");
        }
        if (input.testType() == null) {
            throw new InputInvalidException("testType is mandatory");
        }
        if (input.attachPeriod() == null) {
            throw new InputInvalidException("attachPeriod is mandatory");
        }
        if (input.detachPeriod() == null) {
            throw new InputInvalidException("detachPeriod is mandatory");
        }
        if (input.taskName() == null) {
            throw new InputInvalidException("taskName is mandatory");
        }
        if(input.startDate()!= null && LocalDate.now().isAfter(input.startDate())){
            throw new InputInvalidException("startDate cannot be in the past");
        }
    }

    private LocalDate calculateEndDate(BladeTaskInput input) {
        if (input.startDate() != null && input.duration() != null) {
            return input.startDate().plusDays(input.duration());
        }
        return null;
    }

}


