package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.BladeProjectLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;

import java.util.List;


@Controller
public class BladeProjectController {

    private final BladeProjectLogic bladeProjectLogic;

    public BladeProjectController(BladeProjectLogic bladeProjectLogic) {
        this.bladeProjectLogic = bladeProjectLogic;
    }

    @QueryMapping
    public List<BladeProject> AllBladeProjects() {return bladeProjectLogic.findAll();
    }

    @MutationMapping
    public BladeProject createBladeProject(@Argument String name, @Argument String customer, @Argument String projectLeader) {
        return bladeProjectLogic.createProject(name, customer, projectLeader);
    }

    @MutationMapping
    public String deleteBladeProject(@Argument Long id) {
        return bladeProjectLogic.deleteProject(id);
    }
}