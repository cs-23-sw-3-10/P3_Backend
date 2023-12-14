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

    public TechnicianController(TechnicianLogic technicianLogic){
        this.technicianLogic=technicianLogic;
    }


    /**
     * This method fetches all the technicians
     * @return all the technicians
     */
    @QueryMapping
    public List<Technician> AllTechnicians(){
        return technicianLogic.findAll();
    }

    /**
     * This method fetches technician by type
     * @param type
     * @return the technician with the passed type
     */
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

    /**
     * This method creates a new technician
     * @param type the type of technician
     * @param maxWorkHours the maximum work hours of the technician
     * @param count the amount of technicians of this type there is
     * @return the created technician
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Technician CreateTechnician(@Argument String type, @Argument Integer maxWorkHours, @Argument Integer count){
        return technicianLogic.CreateTechnician(type, maxWorkHours, count);
    }

    /**
     * This method deletes a technician
     * @param type the type of technician that is deleted
     * @return the deleted technician
     */

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Technician DeleteTechnician(@Argument String type) {
        return technicianLogic.deleteTechnician(type);
    }
}
