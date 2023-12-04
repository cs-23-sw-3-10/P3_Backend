package sw_10.p3_backend.Controller;

//Spring Imports
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

//Model + Logic
import sw_10.p3_backend.Logic.BladeProjectLogic;
import sw_10.p3_backend.Model.*;
import java.util.List;


@Controller
public class BladeProjectController {

    private final BladeProjectLogic bladeProjectLogic;

    public BladeProjectController(BladeProjectLogic bladeProjectLogic) {
        this.bladeProjectLogic = bladeProjectLogic;
    }

    //Returns all Blade Projects
    @QueryMapping
    public List<BladeProject> AllBladeProjects() {return bladeProjectLogic.findAll();
    }

    //Returns all Blade Projects, based on if user is looking at "active" or "draft(inactive)" schedule
    @QueryMapping
    public List<BladeProject> AllBladeProjectsBySchedule(@Argument boolean isActive) {
        return bladeProjectLogic.findAllBySchedule(isActive);
    }

    //PreAuthorize: Checks if the user invoking the method is authorized to do so
    //Authorization criteria: User is logged in on the website -> Editing privileges
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeProject createBladeProject(@Argument String name, @Argument String customer, @Argument String projectLeader, @Argument List<ResourceOrderInput> resourceOrders) {
        return bladeProjectLogic.createProject(name, customer, projectLeader, resourceOrders);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteBladeProject(@Argument Long id) {
        return bladeProjectLogic.deleteProject(id);
    }

    @MutationMapping
    public BladeProject updateBladeProject(@Argument Long bpId, @Argument BladeProjectInput updates) {
        return bladeProjectLogic.updateBladeProject(bpId, updates);
    }


}