package sw_10.p3_backend.Logic;


import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.InputInvalidException;


import java.util.List;


@Service
public class BladeProjectLogic {


    private final BladeProjectRepository BladeProjectRepository;
    private final ScheduleRepository scheduleRepository;


    public BladeProjectLogic(BladeProjectRepository bladeProjectRepository, ScheduleRepository scheduleRepository){
        this.BladeProjectRepository = bladeProjectRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public BladeProject createProject(String name, String customer, String projectLeader) {
            Schedule schedule = scheduleRepository.findScheduleByIsActive(false);//Makes sure all new assigned projects are assigned to the draft schedule
            BladeProject project = new BladeProject(schedule, name, customer, projectLeader);
            BladeProjectRepository.save(project);
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
        return BladeProjectRepository.findAll();
    }
}

