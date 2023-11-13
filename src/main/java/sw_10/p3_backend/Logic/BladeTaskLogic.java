package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
            //get bladeproject from input and find it in db and save it to bt
            BladeProject bladeProject = bladeProjectRepository.findById((long) input.bladeProjectId()).get();

            LocalDate endDate = null;
            //calculate end date from start date and duration
            if(input.startDate() != null) {
                endDate = input.startDate().plusDays(input.duration());
            }

            BladeTask newBladeTask = new BladeTask(input.startDate(), endDate, input.duration(), input.testType(),
                        input.attachPeriod(), input.detachPeriod(), input.taskName(),
                        input.testRig(), bladeProject);

            return bladeTaskRepository.save(newBladeTask);
        }

        public List<BladeTask> findAll(){
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
}

