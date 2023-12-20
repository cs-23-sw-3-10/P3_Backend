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

import java.util.ArrayList;
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

    /**
     * This method returns all blade projects from view schedule or edit schedule
     * @param isActive determines whether you fetch from view schedule or edit schedule
     * @return Returns all blade projects from one of the schedules
     */
    @QueryMapping
    public List<BladeProject> AllBladeProjectsBySchedule(@Argument boolean isActive) {
        return bladeProjectLogic.findAllBySchedule(isActive);
    }

    /**
     * This method gets a blade project with the passed id
     * @param id the id of the blade project you want to fetch
     * @return the blade project with the passed id
     */
    @QueryMapping
    public BladeProject BladeProjectById(@Argument Long id){
        return bladeProjectLogic.findBladeProjectById(id);
    }

    //PreAuthorize: Checks if the user invoking the method is authorized to do so
    //Authorization criteria: User is logged in on the website -> Editing privileges
    /**
     * This method creates a new blade project with the passed values
     * @param name
     * @param customer
     * @param projectLeader
     * @param resourceOrders
     * @return the created project
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeProject createBladeProject(@Argument String name, @Argument String customer, @Argument String projectLeader, @Argument List<ResourceOrderInput> resourceOrders) {
        return bladeProjectLogic.createProject(name, customer, projectLeader, resourceOrders);
    }

    /**
     * Updates the information of a blade project with a certain id
     * @param bpId
     * @param updates contains all the updated information about a blade project
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeProject updateBladeProject(@Argument Long bpId, @Argument BladeProjectInput updates) {
        return bladeProjectLogic.updateBladeProject(bpId, updates);
    }

    /**
     * Deletes a blade project with a certain id if it has no blade tasks
     * @param id
     * @return a string detailing the deleted blade project or an error message
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteBladeProject(@Argument Long id){
        return bladeProjectLogic.deleteBladeProject(id);
    }


}