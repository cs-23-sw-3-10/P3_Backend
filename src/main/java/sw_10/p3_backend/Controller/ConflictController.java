package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import sw_10.p3_backend.Logic.ConflictLogic;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Repository.ConflictRepository;

import java.util.List;

@Controller
public class ConflictController {

    private final ConflictRepository conflictRepository;

    @Autowired
    private ConflictLogic conflictlogic;

    public ConflictController(ConflictRepository conflictRepository){
        this.conflictRepository = conflictRepository;
    }
    @QueryMapping
    public List<Conflict> AllConflicts(){
        return conflictRepository.findAll();
    } //Consider adding logic and error handling
    
}
