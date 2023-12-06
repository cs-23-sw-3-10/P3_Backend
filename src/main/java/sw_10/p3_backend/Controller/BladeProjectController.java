package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import reactor.core.publisher.Sinks;
import sw_10.p3_backend.Logic.BladeProjectLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeProjectInput;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


@Controller
public class BladeProjectController {

    private final BladeProjectLogic bladeProjectLogic;

    private final BladeProjectRepository bladeProjectRepository;



    public BladeProjectController(BladeProjectLogic bladeProjectLogic, BladeProjectRepository bladeProjectRepository) {
        this.bladeProjectLogic = bladeProjectLogic;
        this.bladeProjectRepository = bladeProjectRepository;
        
    }
    

    @QueryMapping
    public List<BladeProject> AllBladeProjects() {return bladeProjectLogic.findAll();
    }
    
    @QueryMapping
    public List<BladeProject> AllBladeProjectsBySchedule(@Argument boolean isActive) {
        return bladeProjectLogic.findAllBySchedule(isActive);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeProject createBladeProject(@Argument String name, @Argument String customer, @Argument String projectLeader) {
        return bladeProjectLogic.createProject(name, customer, projectLeader);
    }

    /*@PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteBladeProject(@Argument Long id) {
        return bladeProjectLogic.deleteProject(id);
    }*/

    @MutationMapping
    public BladeProject updateBladeProject(@Argument Long bpId, @Argument BladeProjectInput updates) {
        return bladeProjectLogic.updateBladeProject(bpId, updates);
    }

    public String deleteBladeProject(@Argument Long id){
        return bladeProjectLogic.deleteBladeProject(id);
    }


}