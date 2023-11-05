package sw_10.p3_backend.Controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw_10.p3_backend.Logic.ProjectLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;

import java.util.List;
import java.util.Map;

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
