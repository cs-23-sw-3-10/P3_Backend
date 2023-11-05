package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.IdNotFoundException;
import java.util.List;

@Controller
public class EngineerController {
    private final EngineerRepository engineerRepository;

    public EngineerController(EngineerRepository engineerRepository){ //dependency injection
        this.engineerRepository=engineerRepository;
    }

    @QueryMapping
    public List<Engineer> AllEngineers(){
        return engineerRepository.findAll();
    }

    @QueryMapping
    public Engineer EngineerById(@Argument Integer id){
        try {
            return engineerRepository.findById(id.longValue()).orElseThrow(() -> new IdNotFoundException("No engineer found with id:" + id));//Consider adding logic layer and move error handling
        }catch (IdNotFoundException e)
        {
            System.out.println("Engineer not found: " + e.getMessage());
            throw e;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
