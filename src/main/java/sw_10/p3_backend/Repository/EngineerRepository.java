package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Model.Equipment;

@Repository
public interface EngineerRepository extends JpaRepository<Engineer,Long> {

    /**
     * This query fetches the engineer with a certain name
     * @param name the name of the engineer
     * @return the engineer with the passed name
     */
    Engineer findByName(String name);

}
