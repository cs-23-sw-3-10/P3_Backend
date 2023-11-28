package sw_10.p3_backend.Logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BladeTaskLogic {
    private final BladeTaskRepository bladeTaskRepository;
    private final BladeProjectRepository bladeProjectRepository;
    private final BookingLogic bookingLogic;
    private final ResourceOrderLogic resourceOrderLogic;
    private final BladeProjectLogic bladeProjectLogic;
    private final BookingRepository bookingRepository;

    @Autowired
    private ConflictLogic conflictLogic;

    @Autowired
    public BladeTaskLogic(BladeTaskRepository bladeTaskRepository, BladeProjectRepository bladeProjectRepository,
                          BookingLogic bookingLogic, ResourceOrderLogic resourceOrderLogic, BladeProjectLogic bladeProjectLogic,
                          BookingRepository bookingRepository) {
        this.bladeTaskRepository = bladeTaskRepository;
        this.bladeProjectRepository = bladeProjectRepository;
        this.bookingLogic = bookingLogic;
        this.resourceOrderLogic = resourceOrderLogic;
        this.bookingRepository = bookingRepository;
        this.bladeProjectLogic = bladeProjectLogic;

    }

    @PostConstruct
    public void init() {
        conflictLogic.setBladeTaskLogic(this);
    }

    public String deleteTask(Integer id) {
        try {
            bladeTaskRepository.deleteById(id.longValue());
            return "BT deleted";
        } catch (Exception e) {
            return "Error deleting BT" + e;
        }
    }

    //TODO: add validation of equipmentAssignmentStatus of resource orders "Must be consecutive" or add logic to handle this e.g. multiple bookings pr resource order
    public BladeTask createBladeTask(BladeTaskInput input) {
        System.out.println(input.startDate());
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        validateBladeTaskInput(input);

        // Find the blade project in the database
        BladeProject bladeProject = getBladeProject(Long.valueOf(input.bladeProjectId()));

        LocalDate startDate = input.startDate();
        Integer totalDuration = input.duration() + input.attachPeriod() + input.detachPeriod();
        // Calculate the end date of the blade task
        LocalDate endDate = calculateEndDate(startDate, totalDuration);

        //Set testrig to 0 if none is provided
        int noTestRigAssignedValue = 0;
        int testRigValue = Optional.ofNullable(input.testRig()).orElse(noTestRigAssignedValue);
        System.out.println(testRigValue);

        // Create a new BladeTask instance
        BladeTask newBladeTask = new BladeTask(
                input.startDate(),
                endDate,
                totalDuration,
                input.testType(),
                input.attachPeriod(),
                input.detachPeriod(),
                input.taskName(),
                testRigValue,
                bladeProject
        );


        // Create resource orders for the blade task (if any)
        List<ResourceOrder> resourceOrders = handleResourceOrders(input, newBladeTask);

        // Save the new BladeTask in the database
        bladeTaskRepository.save(newBladeTask);

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided

        if (testRigValue != 0 && resourceOrders != null) {
            bookingLogic.createBookings(resourceOrders, newBladeTask);
        }
        bladeProjectLogic.updateBladeProject(newBladeTask.getBladeProjectId());
        // Return the new BladeTask
        return newBladeTask;
    }

    private BladeProject getBladeProject(Long bladeProjectId) {
        return bladeProjectRepository.findById(bladeProjectId)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + bladeProjectId));
    }

    private List<ResourceOrder> handleResourceOrders(BladeTaskInput input, BladeTask newBladeTask) {
        if (input.resourceOrders() != null) {
            return resourceOrderLogic.createResourceOrders(input.resourceOrders(), newBladeTask);
        }
        return null;
    }

    public List<BladeTask> findAll() {
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

    private void validateBladeTaskInput(BladeTaskInput input) {

        if ((input.startDate() == null && input.testRig() != null) || (input.startDate() != null && input.testRig() == null)) {
            throw new InputInvalidException("Both startDate and testRig must be provided together");
        }
        if (input.testRig() != null && input.testRig() < 0) {
            throw new InputInvalidException("testRig cannot be negative");
        }
        if (input.bladeProjectId() == null) {
            throw new InputInvalidException("bladeProjectId is mandatory");
        }
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
        if (input.startDate() != null && LocalDate.now().isAfter(input.startDate())) {
            throw new InputInvalidException("startDate cannot be in the past");
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, Integer duration) {
        if (startDate != null && duration != null) {
            return startDate.plusDays(duration);
        }
        return null;
    }

    public BladeTask updateStartAndDurationBladeTask(Long id, String startDate, Integer duration, Integer testRig) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        LocalDate startDateParsed;
        if(startDate.equals("undefined")){
            startDateParsed = null;
        }else {
            //parsed startDate to LocalDate
            startDateParsed = LocalDate.parse(startDate);
        }

        bladeTaskToUpdate.setStartDate(startDateParsed);
        bladeTaskToUpdate.setDuration(duration);
        // Calculate the end date of the blade task
        bladeTaskToUpdate.setEndDate(calculateEndDate(startDateParsed, duration));
        bladeTaskToUpdate.setTestRig(testRig);

        //Set testrig to 0 if none is provided
        int noTestRigAssignedValue = 0;
        int testRigValue = Optional.of(bladeTaskToUpdate.getTestRig()).orElse(noTestRigAssignedValue);
        System.out.println(testRigValue);

        //Remove old bookings
        bookingLogic.removeBookings(bladeTaskToUpdate);


        // Save the new BladeTask in the database
        bladeTaskRepository.save(bladeTaskToUpdate);

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided
        if (testRigValue != 0 && bladeTaskToUpdate.getResourceOrders() != null) {
            System.out.println("Creating bookings");
            bookingLogic.createBookings(bladeTaskToUpdate.getResourceOrders(), bladeTaskToUpdate);
        }


        bladeProjectLogic.updateBladeProject(bladeTaskToUpdate.getBladeProjectId());
        bladeTaskRepository.save(bladeTaskToUpdate);


        // Return the new BladeTask
        return bladeTaskToUpdate;
    }

    public List<BladeTask> bladeTasksInRange(String startDate, String endDate, boolean isActive) {
        return bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive);
    }

    public List<BladeTask> bladeTasksPending(){
        return bladeTaskRepository.bladeTasksPending();
    }

    //TODO: Find a better way to do this?
    public List<BladeTask> getRelatedBladeTasksByEquipmentType(String equipmentName, LocalDate startDate, LocalDate endDate) {
        System.out.println("Getting relevant bookings");
        List<Booking> bookings = bookingRepository.findBookedEquipmentByTypeAndPeriod(equipmentName, startDate, endDate); //Implement

        System.out.println("Bookings:");
        System.out.println(bookings);

        List<BladeTask> bladeTasks = new ArrayList<>();
        for (Booking booking : bookings) {
            System.out.println(booking);
            BladeTask tempBladeTask = booking.fetchBladeTask();
            System.out.println(tempBladeTask);
            bladeTasks.add(tempBladeTask);
        }
        System.out.println("BladeTasks:");
        System.out.println(bladeTasks);

        //TODO: Ensure uniqueness of list items and make sure they are only from 1 of the 2 schedules

        return bladeTasks;
    }

    public void addRelatedConflict(BladeTask bladeTask, Conflict conflict) {
        bladeTask.addRelatedConflict(conflict);
        bladeTaskRepository.save(bladeTask);
    }

    public void removeRelatedConflict(BladeTask bladeTask, Conflict conflict) {
        bladeTask.removeRelatedConflict(conflict);
        bladeTaskRepository.save(bladeTask);
    }
}


