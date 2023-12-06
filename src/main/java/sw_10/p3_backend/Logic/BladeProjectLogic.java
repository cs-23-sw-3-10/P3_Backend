package sw_10.p3_backend.Logic;


import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;

import sw_10.p3_backend.Repository.ResourceOrderRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.InputInvalidException;


import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@Service
public class BladeProjectLogic {
    private final BladeProjectRepository BladeProjectRepository;
    private final ResourceOrderLogic resourceOrderLogic;
    private final BookingLogic bookingLogic;
    private final ScheduleRepository scheduleRepository;

    public BladeProjectLogic(BladeProjectRepository bladeProjectRepository, ResourceOrderLogic resourceOrderLogic, BookingLogic bookingLogic, ScheduleRepository scheduleRepository){
        this.BladeProjectRepository = bladeProjectRepository;
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
            BladeProjectRepository.save(project);

            return project;
    }
    private List<ResourceOrder> handleResourceOrders(List<ResourceOrderInput> resourceOrderInputs, BladeProject bladeProject) {
        if (resourceOrderInputs != null) {
            return resourceOrderLogic.createResourceOrdersBladeProject(resourceOrderInputs, bladeProject);
        }
        return null;
    }


    public String deleteProject(Long id) {
        BladeProject project = BladeProjectRepository.findById(id).orElseThrow(() -> new InputInvalidException("Project with id " + id + " not found"));
        if(project.getBladeTasks().isEmpty()) {//makes sure the project has no tasks before deleting
            BladeProjectRepository.deleteById(id);
            return "Project deleted";
        }
        else {
            return "Project has tasks"; //Create logic to delete tasks before deleting project
        }
    }

    public List<BladeProject> findAll(){
        return BladeProjectRepository.findAll();
    }

    public List<BladeProject> findAllBySchedule(boolean isActive){
        return BladeProjectRepository.findAllBySchedule(isActive);
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
                if (finalEndDate == null || bladeTask.getEndDate().isBefore(finalEndDate)) {
                    finalEndDate = bladeTask.getEndDate();
                }
            }
        };

        //Create bookings if there are existing resource orders and no bookings
        if(!bladeProject.getResourceOrders().isEmpty() && bladeProject.getBookings().isEmpty()){
            bookingLogic.createBookings(bladeProject.getResourceOrders(), bladeProject);
        }

        if(bladeProject.getStartDate() == null || bladeProject.getEndDate() == null || finalStartDate != null && finalStartDate.isBefore(bladeProject.getStartDate()) || finalEndDate != null && finalEndDate.isAfter(bladeProject.getEndDate())) {
            bookingLogic.updateBookings(bladeProject, finalStartDate, finalEndDate);
        }


        BladeProjectRepository.save(bladeProject);
    }

    public BladeProject updateBladeProject(Long bpId, BladeProjectInput updates) {
        BladeProject BPToUpdate = BladeProjectRepository.findById(bpId)
                .orElseThrow(() -> new InputInvalidException("Project with id " + updates.scheduleId() + " not found"));

        if (!BPToUpdate.getProjectName().equals(updates.projectName())){
            BPToUpdate.setProjectName(updates.projectName());
        }
        if (!BPToUpdate.getCustomer().equals(updates.customer())){
            BPToUpdate.setCustomer(updates.customer());
        }
        if (!BPToUpdate.getProjectLeader().equals(updates.projectLeader())){
            BPToUpdate.setProjectLeader(updates.projectLeader());
        }

        BladeProjectRepository.save(BPToUpdate);

        return BPToUpdate;
    }

    public List<BladeProject> lookUpBladeData() {
        return BladeProjectRepository.findAll();
    }
}





