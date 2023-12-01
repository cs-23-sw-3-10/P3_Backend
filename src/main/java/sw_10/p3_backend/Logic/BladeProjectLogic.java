package sw_10.p3_backend.Logic;


import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeProjectInput;
import sw_10.p3_backend.Model.ResourceOrderInput;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.InputInvalidException;


import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@Service
public class BladeProjectLogic {


    private final BladeProjectRepository BladeProjectRepository;
    private final ScheduleRepository scheduleRepository;




    public BladeProjectLogic(BladeProjectRepository bladeProjectRepository, ScheduleRepository scheduleRepository){
        this.BladeProjectRepository = bladeProjectRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public BladeProject createProject(String name, String customer, String projectLeader, List<ResourceOrderInput> resourceOrders) {
            Schedule schedule = scheduleRepository.findScheduleByIsActive(false); //Makes sure all new assigned projects are assigned to the draft schedule
            BladeProject project = new BladeProject(schedule, name, customer, projectLeader, generateRandomColorHexCode());

            BladeProjectRepository.save(project);
            List<BladeProject> bladeProjects = BladeProjectRepository.findAll();
            BladeProject.setBladeProjectList(bladeProjects);
            return project;
    }

    public String deleteProject(Long id) {
        BladeProject project = BladeProjectRepository.findById(id).orElseThrow(() -> new InputInvalidException("Project with id " + id + " not found"));
        if(project.getBladeTasks().isEmpty()) {//makes sure the project has no tasks before deleting
            BladeProjectRepository.deleteById(id);
            return "Project deleted";
        }
        else {
            return "Project has tasks";//Create logic to delete tasks before deleting project
        }
    }

    public List<BladeProject> findAll(){
        List<BladeProject> bladeProjects = BladeProjectRepository.findAll();
        BladeProject.setBladeProjectList(bladeProjects);
        for (BladeProject bladeProject : bladeProjects) {
            System.out.println(bladeProject.getBladeTasks().size());

        }


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
        //set bladeProject start and end date to the earliest and latest bladeTask start and end date
        bladeProject.getBladeTasks().forEach(bladeTask -> {
            if(bladeTask.getStartDate()!=null && bladeTask.getEndDate()!=null) {// Pending blade tasks does not contribute to project start- and end date
                if (bladeProject.getStartDate() == null || bladeTask.getStartDate().isBefore(bladeProject.getStartDate())) {
                    bladeProject.setStartDate(bladeTask.getStartDate());
                }
                if (bladeProject.getEndDate() == null || bladeTask.getEndDate().isAfter(bladeProject.getEndDate())) {
                    bladeProject.setEndDate(bladeTask.getEndDate());
                }
            }
        });

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





