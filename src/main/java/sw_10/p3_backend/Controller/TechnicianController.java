package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;
import java.util.List;

@Controller
public class TechnicianController {
    private final TechnicianRepository technicianRepository;


    public TechnicianController(TechnicianRepository technicianRepository){
        this.technicianRepository=technicianRepository;
    }


    @QueryMapping
    public List<Technician> AllTechnicians(){
        return technicianRepository.findAll();
    }

    @QueryMapping
    public Technician TechnicianByType(@Argument String type){
        return technicianRepository.findByType(type);
    }
}
