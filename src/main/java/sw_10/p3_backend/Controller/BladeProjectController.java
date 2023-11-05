package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.ProjectLogic;
import sw_10.p3_backend.Model.BladeProject;
import java.util.List;


@Controller
public class BladeProjectController {

    private final ProjectLogic projectLogic;

    public BladeProjectController(ProjectLogic projectLogic) {
        this.projectLogic = projectLogic;
    }

    @QueryMapping
    public List<BladeProject> AllBladeProjects() {
        return projectLogic.findAll();
    }

    @MutationMapping
    public BladeProject createBladeProject(@Argument Integer scheduleId, @Argument String name, @Argument String customer, @Argument String projectLeader) {
        return projectLogic.createProject(scheduleId, name, customer, projectLeader);
    }

}
