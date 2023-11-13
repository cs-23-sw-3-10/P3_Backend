package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.EngineerLogic;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.InputInvalidException;

import java.util.List;

@Controller
public class EngineerController {
    private final EngineerRepository engineerRepository;
    private final EngineerLogic engineerLogic;

    public EngineerController(EngineerRepository engineerRepository, EngineerLogic engineerLogic){ //dependency injection
        this.engineerRepository=engineerRepository;
        this.engineerLogic = engineerLogic;
    }

    @QueryMapping
    public List<Engineer> AllEngineers(){
        return engineerRepository.findAll();
    }

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
}
