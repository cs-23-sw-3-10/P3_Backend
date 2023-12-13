package sw_10.p3_backend.Logic;


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

    /**
     * Creates a new BladeProject and saves it in the database
     * @param name The name of the project
     * @param customer The customer of the project
     * @param projectLeader The project leader of the project
     * @param resourceOrderInput The resource orders of the project
     * @return The created BladeProject
     */
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

    /**
     * This method creats resource orders for a blade project from a list of resource order inputs
     * @param resourceOrderInputs The list of resource order inputs
     * @param bladeProject The blade project to create resource orders for
     * @return A list of resource orders
     */
    private List<ResourceOrder> handleResourceOrders(List<ResourceOrderInput> resourceOrderInputs, BladeProject bladeProject) {
        if (resourceOrderInputs != null) {
            return resourceOrderLogic.createResourceOrdersBladeProject(resourceOrderInputs, bladeProject);
        }
        return null;
    }


    /**
     * Deletes a BladeProject from the database if it has no tasks
     * @param id The id of the BladeProject to delete
     * @return A string with the result of the deletion
     */
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

    /**
     * Finds all BladeProjects in the database
     * @return A list of all BladeProjects
     */
    public List<BladeProject> findAll(){
        return bladeProjectRepository.findAll();
    }

    /**
     * Finds all BladeProjects in the database that belong to a specific schedule
     * @param isActive The boolean value that determines which schedule to find BladeProjects for
     * @return A list of all BladeProjects that belong to the schedule
     */
    public List<BladeProject> findAllBySchedule(boolean isActive){
        return bladeProjectRepository.findAllBySchedule(isActive);
    }

    //TODO: temp solution, should be handled by frontend with a color picker

    /**
     * Generates a random color for a BladeProject that is not too dark to be able to read black text on it
     * @return
     */
    private static String generateRandomColorHexCode() {
        Random random = new Random();

        int red, green, blue;
        do {
            // Generate random RGB values
            red = random.nextInt(256);   // 0-255
            green = random.nextInt(256); // 0-255
            blue = random.nextInt(256);  // 0-255
        } while (!isLightColor(red, green, blue));

        // Convert to hexadecimal
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    /**
     * Ensure that the color is light enough to be readable on a white background and that black text will not be unreadable
     * @param red the red value of the color
     * @param green the green value of the color
     * @param blue the blue value of the color
     * @return true if the color is light enough, false if not
     */
    private static boolean isLightColor(int red, int green, int blue) {
        // Calculate luminance
        double luminance = 0.299 * red + 0.587 * green + 0.114 * blue;
        // A threshold value, above which the color can be considered light
        // 128 is a middle value for an 8-bit color component, adjust as needed
        return luminance > 128;
    }

    /**
     * Updates the start and end date of a BladeProject based on the start and end date of its BladeTasks
     * @param bladeProject The BladeProject to update
     */
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

    /**
     * Updates a BladeProject with new values
     * @param BPId The id of the BladeProject to update
     * @param updates The new values for the BladeProject
     * @return The updated BladeProject
     */
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

    /**
     * Deletes a BladeProject from the database if it has no BladeTasks
     * @param id The id of the BladeProject to delete
     * @return A string with the result of the deletion
     */
    public String deleteBladeProject(Long id){
        BladeProject bladeProjectToDelete = bladeProjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + id));
        //Checks if the bladeProject has any bladeTasks, as it can only be deleted if it has no bladeTasks
        if(bladeProjectToDelete.getBladeTasks().isEmpty()){
            bladeProjectRepository.delete(bladeProjectToDelete);
            return "BladeProject with id: " + bladeProjectToDelete.getId() + " has been deleted";
        }else{

            return "BladeProject with id: " + bladeProjectToDelete.getId() + " has bladetasks and can therefore not be deleted";
        }
    }

    /**
     * Finds a BladeProject in the database by its id
     * @param id The id of the BladeProject to find
     * @return The found BladeProject
     */
    public BladeProject findBladeProjectById(Long id){
        return bladeProjectRepository.findBladeProjectById(id);
    }
}





