package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.BladeTaskLogic;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.exception.InputInvalidException;

import java.util.List;


@Controller
public class BladeTaskController {

    private final BladeTaskLogic bladeTaskLogic;

    BladeTaskController(BladeTaskLogic bladeTaskLogic){
        this.bladeTaskLogic = bladeTaskLogic;
    }

    @QueryMapping
    public List<BladeTask> AllBladeTasksInRange(@Argument String startDate, @Argument String endDate){
        return bladeTaskLogic.bladeTasksInRange(startDate, endDate);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<BladeTask> AllBladeTasks(){
        return bladeTaskLogic.findAll();
    }

    @QueryMapping
    public BladeTask BladeTaskById(@Argument Integer id) {
        try {
            if (id == null) { // Just in case. Should be caught by GraphQL validation.
                throw new InputInvalidException("cannot parse null");
            }
            return bladeTaskLogic.findOne(id);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteBladeTask(@Argument Integer id){
        return bladeTaskLogic.deleteTask(id);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeTask createBladeTask(@Argument BladeTaskInput bladeTask){
        return bladeTaskLogic.createBladeTask(bladeTask);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeTask updateStartAndDurationBladeTask(@Argument Long id, @Argument String startDate, @Argument Integer duration, @Argument Integer testRig){
        return bladeTaskLogic.updateStartAndDurationBladeTask(id, startDate, duration, testRig);
    }
}
