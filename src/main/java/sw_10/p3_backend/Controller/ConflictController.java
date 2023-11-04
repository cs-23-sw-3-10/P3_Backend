package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Repository.ConflictRepository;

import java.util.List;

@Controller
public class ConflictController {

    private final ConflictRepository conflictRepository;

    public ConflictController(ConflictRepository conflictRepository){
        this.conflictRepository = conflictRepository;
    }
    @QueryMapping
    public List<Conflict> conflicts(){
        return conflictRepository.findAll();
    }

}
