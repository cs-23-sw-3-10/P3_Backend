package sw_10.p3_backend.Logic;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.ScheduleCreationException;
import sw_10.p3_backend.exception.ScheduleNotFoundException;

import java.util.List;


@Service
public class ProjectLogic {
    

    private final BladeProjectRepository BladeProjectRepository;
    private final ScheduleRepository scheduleRepository;

    public ProjectLogic(BladeProjectRepository bladeProjectRepository, ScheduleRepository scheduleRepository){
        this.BladeProjectRepository = bladeProjectRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public BladeProject createProject(Integer scheduleId, String name, String customer, String projectLeader) {
        try {
            Schedule schedule = scheduleRepository.findById(Long.valueOf(scheduleId)).orElseThrow(
                    ()->new ScheduleNotFoundException("No schedule found with id:" + scheduleId));
            BladeProject project = new BladeProject(schedule, name, customer, projectLeader);
            return BladeProjectRepository.save(project);
        } catch (ScheduleNotFoundException e) {
            System.out.println("schedule not found: " + e.getMessage());
            throw e;
        }catch (Exception e) {
            throw new ScheduleCreationException("Error creating project",e);

        }
    }

    public List<BladeProject> findAll(){
        return BladeProjectRepository.findAll();
    }
}

