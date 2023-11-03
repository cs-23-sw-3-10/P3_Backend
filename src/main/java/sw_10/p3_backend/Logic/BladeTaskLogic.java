package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;

import java.util.Map;
import java.util.Objects;

@Service
public class BladeTaskLogic {

    private final BladeTaskRepository bladeTaskRepository;
    private final BladeProjectRepository bladeProjectRepository;


    @Autowired
    public BladeTaskLogic(BladeTaskRepository bladeTaskRepository, BladeProjectRepository bladeProjectRepository) {
        this.bladeTaskRepository = bladeTaskRepository;
        this.bladeProjectRepository = bladeProjectRepository;
    }

    public String deleteAllTasks(){
        try {
            bladeTaskRepository.deleteAll();
            return "All tasks deleted";
        }
        catch (Exception e) {
            return "Error deleting tasks" + e;
            }
        }

    public String deleteTask(Map<String, String> body){
        try {
            long id=Long.parseLong(body.get("id"));
            bladeTaskRepository.deleteById(id);
            return "BT deleted";
        }
        catch (Exception e) {
            return "Error deleting BT" + e;
        }
    }

        public BladeTask createBladeTask(Map<String, String> body){
            int startDate = Integer.parseInt(body.get("startDate"));
            int duration =  Integer.parseInt(body.get("duration"));
            long bpid = Long.parseLong(body.get("bpId"));
            BladeProject bp = bladeProjectRepository.findById(bpid).get();
            BladeTask nybt = new BladeTask(startDate, duration, bp);
            return bladeTaskRepository.save(nybt);
        }
    }

