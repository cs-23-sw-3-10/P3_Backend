package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.EngineerLogic;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.InputInvalidException;

import java.util.List;

@Controller
public class    EngineerController {
    private final EngineerLogic engineerLogic;

    public EngineerController(EngineerLogic engineerLogic){ //dependency injection
        this.engineerLogic = engineerLogic;
    }

    /**
     * This method fetches all engineers
     * @return all engineers
     */
    @QueryMapping
    public List<Engineer> AllEngineers(){
        return engineerLogic.findAll();
    }

    /**
     * Returns an engineer with a certain id
     * @param id
     * @return the engineer with the passed id
     */
    @QueryMapping
    public Engineer EngineerById(@Argument Integer id) {
        try {
            if (id == null) { // Just in case. Should be caught by GraphQL validation.
                throw new InputInvalidException("cannot parse null");
            }
            return engineerLogic.EngineerById(id);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Creates a new engineer
     * @param name the name of the engineer
     * @param maxWorkHours the maximum wortk hours that the engineer can work
     * @return the created engineer
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Engineer CreateEngineer(@Argument String name, @Argument Integer maxWorkHours){
        return engineerLogic.CreateEngineer(name, maxWorkHours);
    }

    /**
     * Deletes an engineer
     * @param name the name of the engineer that should be deleted
     * @return the deleted engineer
     */
    @MutationMapping
    public Engineer DeleteEngineer(@Argument String name) {
        return engineerLogic.deleteEngineer(name);
    }
}
