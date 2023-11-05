package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw_10.p3_backend.Logic.BladeTaskLogic;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.Model.Conflict;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Controller
public class BladeTaskController {

    private final BladeTaskLogic bladeTaskLogic;

    BladeTaskController(BladeTaskLogic bladeTaskLogic){
        this.bladeTaskLogic = bladeTaskLogic;
    }

    @QueryMapping
    public List<BladeTask> AllBladeTasks(){
        return bladeTaskLogic.findAll();
    }

    @QueryMapping
    public BladeTask BladeTaskById(@Argument Integer id){
        return bladeTaskLogic.findOne(id);
    }

    @MutationMapping
    public String deleteBladeTask(@Argument Integer id){
        return bladeTaskLogic.deleteTask(id);
    }

    @MutationMapping
    public BladeTask createBladeTask(@Argument BladeTaskInput bladeTask){
        return bladeTaskLogic.createBladeTask(bladeTask);
    }
}
