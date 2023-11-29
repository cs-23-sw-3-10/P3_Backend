package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.TechnicianLogic;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;
import java.util.List;

@Controller
public class TechnicianController {
    private final TechnicianLogic technicianLogic;
    private final TechnicianRepository technicianRepository;


    public TechnicianController(TechnicianLogic technicianLogic, TechnicianRepository technicianRepository){
        this.technicianLogic=technicianLogic;
        this.technicianRepository = technicianRepository;
    }



    @QueryMapping
    public List<Technician> AllTechnicians(){
        return technicianRepository.findAll();
    }

    @QueryMapping
    public Technician TechnicianByType(@Argument String type) {
        try {
            if (type == null) {
                throw new IllegalArgumentException("cannot parse null");
            }
            return technicianLogic.TechnicianByType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw e;

        }
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Technician CreateTechnician(@Argument String type, @Argument Integer maxWorkHours, @Argument Integer count){
        return technicianLogic.CreateTechnician(type, maxWorkHours, count);
    }
}
