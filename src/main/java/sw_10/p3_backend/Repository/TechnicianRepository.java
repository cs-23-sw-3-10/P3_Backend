package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Model.Technician;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician,Long>{

    /**
     * This query fetches the technician with a certain type
     * @param type the type of technician you wish to fetch
     * @return the technician with the passed type
     */
    Technician findByType(String type);

}
