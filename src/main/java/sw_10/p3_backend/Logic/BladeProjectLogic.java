package sw_10.p3_backend.Logic;


import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeProjectInput;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.ResourceOrderInput;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class BladeProjectLogic {
    private final ResourceOrderLogic resourceOrderLogic;
    private final BookingLogic bookingLogic;
    private final BladeProjectRepository bladeProjectRepository;
    private final ScheduleRepository scheduleRepository;

    public BladeProjectLogic(BladeProjectRepository bladeProjectRepository, ResourceOrderLogic resourceOrderLogic, BookingLogic bookingLogic, ScheduleRepository scheduleRepository){
        this.bladeProjectRepository = bladeProjectRepository;
        this.resourceOrderLogic = resourceOrderLogic;
        this.bookingLogic = bookingLogic;
        this.scheduleRepository = scheduleRepository;
    }

    public BladeProject createProject(String name, String customer, String projectLeader, List<ResourceOrderInput> resourceOrderInput) {
            System.out.println("BP CREATION STARTED");
            System.out.println(resourceOrderInput);
            Schedule schedule = scheduleRepository.findScheduleByIsActive(false); //Makes sure all new assigned projects are assigned to the draft schedule
            BladeProject project = new BladeProject(schedule, name, customer, projectLeader, generateRandomColorHexCode());

            List<ResourceOrder> resourceOrders = handleResourceOrders(resourceOrderInput, project);
            for (ResourceOrder resourceOrder: resourceOrders) {
                project.addResourceOrder(resourceOrder);
            }

            //Saves Blade Project in database
            bladeProjectRepository.save(project);

            return project;
    }
    private List<ResourceOrder> handleResourceOrders(List<ResourceOrderInput> resourceOrderInputs, BladeProject bladeProject) {
        if (resourceOrderInputs != null) {
            return resourceOrderLogic.createResourceOrdersBladeProject(resourceOrderInputs, bladeProject);
        }
        return null;
    }


    public String deleteProject(Long id) {
        BladeProject project = bladeProjectRepository.findById(id).orElseThrow(() -> new InputInvalidException("Project with id " + id + " not found"));
        if(project.getBladeTasks().isEmpty()) {//makes sure the project has no tasks before deleting
            bladeProjectRepository.deleteById(id);
            return "Project deleted";
        }
        else {
            return "Project has tasks"; //Create logic to delete tasks before deleting project
        }
    }

    public List<BladeProject> findAll(){
        return bladeProjectRepository.findAll();
    }

    public List<BladeProject> findAllBySchedule(boolean isActive){
        return bladeProjectRepository.findAllBySchedule(isActive);
    }

    private static String generateRandomColorHexCode() {
        Random random = new Random();

        // Generate random RGB values
        int red = random.nextInt(256);   // 0-255
        int green = random.nextInt(256); // 0-255
        int blue = random.nextInt(256);  // 0-255

        // Convert to hexadecimal
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    public void updateStartAndEndDate(BladeProject bladeProject) {
        //Set bladeProject start and end date to the earliest and latest bladeTask start and end date
        List<BladeTask> bladeTasks = bladeProject.getBladeTasks();
        LocalDate finalStartDate = null;
        LocalDate finalEndDate = null;

        for(BladeTask bladeTask : bladeTasks){
            if(bladeTask.getStartDate()!=null && bladeTask.getEndDate()!=null) {// Pending blade tasks does not contribute to project start- and end date
                if (finalStartDate == null || bladeTask.getStartDate().isBefore(finalStartDate)) {
                    finalStartDate = bladeTask.getStartDate();
                }
                if (finalEndDate == null || bladeTask.getEndDate().isAfter(finalEndDate)) {
                    finalEndDate = bladeTask.getEndDate();
                }
            }
        };

        bladeProject.setStartDate(finalStartDate);
        bladeProject.setEndDate(finalEndDate);

        //Create bookings if there are existing resource orders and no bookings
        if(!bladeProject.getResourceOrders().isEmpty() && bladeProject.getBookings().isEmpty()){
            bookingLogic.createBookings(bladeProject.getResourceOrders(), bladeProject);
        }


            bookingLogic.updateBookings(bladeProject, finalStartDate, finalEndDate);




        bladeProjectRepository.save(bladeProject);
    }

    public BladeProject updateBladeProject(Long BPId, BladeProjectInput updates) {
        BladeProject BPToUpdate = bladeProjectRepository.findById(BPId)
                .orElseThrow(() -> new InputInvalidException("Project with id " + updates.scheduleId() + " not found"));
        BPToUpdate.setProjectName(updates.projectName());
        BPToUpdate.setCustomer(updates.customer());
        BPToUpdate.setProjectLeader(updates.projectLeader());

        resourceOrderLogic.removeResourceOrdersBladeProject(BPId);
        bookingLogic.removeBookingsBladeProject(BPId);
        if(updates.resourceOrders() != null){
            BPToUpdate.setResourceOrders(resourceOrderLogic.createResourceOrdersBladeProject(updates.resourceOrders(), BPToUpdate));
            bookingLogic.createBookings(BPToUpdate.getResourceOrders(), BPToUpdate);
        }else BPToUpdate.setResourceOrders(new ArrayList<>());

        bladeProjectRepository.save(BPToUpdate);

        return BPToUpdate;
    }
    public List<BladeProject> lookUpBladeData() {
        return bladeProjectRepository.findAll();
    }

    public String deleteBladeProject(Long id){
        BladeProject bladeProjectToDelete = bladeProjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + id));

        if(bladeProjectToDelete.getBladeTasks().isEmpty()){
            bladeProjectRepository.delete(bladeProjectToDelete);
            return "BladeProject with id: " + bladeProjectToDelete.getId() + " has been deleted";
        }else{

            return "BladeProject with id: " + bladeProjectToDelete.getId() + " has bladetasks and can therefore not be deleted";
        }

    }
}





