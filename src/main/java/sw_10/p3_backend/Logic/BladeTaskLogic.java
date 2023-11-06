package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;

import java.util.List;
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


    public String deleteTask(Integer id){
        try {
            bladeTaskRepository.deleteById(id.longValue());
            return "BT deleted";
        }
        catch (Exception e) {
            return "Error deleting BT" + e;
        }
    }

        public BladeTask createBladeTask(BladeTaskInput input){

            BladeProject bp = bladeProjectRepository.findById((long) input.bladeProjectId()).get();
            BladeTask nybt = new BladeTask(input.startDate(), input.endDate(), input.duration(),input.testType(), input.attachPeriod(), input.detachPeriod(),input.taskName(),input.testRig(),bp);
            return bladeTaskRepository.save(nybt);
        }

        public List<BladeTask> findAll(){
            return bladeTaskRepository.findAll();
        }

    public BladeTask findOne(Integer id){
        return bladeTaskRepository.findById(Long.valueOf(id)).get();
    }

    }

