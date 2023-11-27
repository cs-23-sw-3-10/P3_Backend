package sw_10.p3_backend.Logic;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.InputInvalidException;


import java.util.List;
import java.util.Random;


@Service
public class BladeProjectLogic {


    private final BladeProjectRepository BladeProjectRepository;
    private final ScheduleRepository scheduleRepository;

    private List<BladeProject> lastKnownState = null;
    private final Sinks.Many<List<BladeProject>> processor = Sinks.many().replay().all();


    public BladeProjectLogic(BladeProjectRepository bladeProjectRepository, ScheduleRepository scheduleRepository){
        this.BladeProjectRepository = bladeProjectRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public BladeProject createProject(String name, String customer, String projectLeader) {
            Schedule schedule = scheduleRepository.findScheduleByIsActive(false);//Makes sure all new assigned projects are assigned to the draft schedule
            BladeProject project = new BladeProject(schedule, name, customer, projectLeader, generateRandomColorHexCode());

            BladeProjectRepository.save(project);
            List<BladeProject> bladeProjects = BladeProjectRepository.findAll();
            BladeProject.setBladeProjectList(bladeProjects);
            onDatabaseUpdate(bladeProjects);
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

    public void updateBladeProject(BladeProject bladeProject) {
        //set bladeProject start and end date to the earliest and latest bladeTask start and end date
        bladeProject.getBladeTasks().forEach(bladeTask -> {
            if(bladeProject.getStartDate() == null || bladeTask.getStartDate().isBefore(bladeProject.getStartDate())) {
                bladeProject.setStartDate(bladeTask.getStartDate());
            }
            if(bladeProject.getEndDate() == null || bladeTask.getEndDate().isAfter(bladeProject.getEndDate())) {
                bladeProject.setEndDate(bladeTask.getEndDate());
            }
        });

        BladeProjectRepository.save(bladeProject);
    }

    public List<BladeProject> lookUpBladeData() {
        return BladeProjectRepository.findAll();
    }

    public void onDatabaseUpdate(List<BladeProject> updatedProjects) {
        if (!updatedProjects.equals(lastKnownState)) {
            lastKnownState = updatedProjects;
            processor.tryEmitNext(updatedProjects);
        }
    }

    public Flux<List<BladeProject>> speedReading() {
        System.out.println("speedReading in logic");
        return processor.asFlux();
    }

}





