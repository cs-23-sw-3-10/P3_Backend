package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeProject;

import java.util.List;

@Repository
public interface BladeProjectRepository extends JpaRepository<BladeProject, Long> {

    /**
     * This query fetches all blade projects that belong to a certain schedule
     * @param isActive which schedule to fetch from
     * @return all blade projects that belong to the passed schedule
     */
    @Query("SELECT bp FROM BladeProject bp WHERE bp.schedule.isActive = :isActive")
    List<BladeProject> findAllBySchedule(boolean isActive);

    /**
     * This query fetches a blade project with a certain id
     * @param id
     * @return the blade project with the passed id
     */
    @Query("SELECT bp FROM BladeProject bp WHERE bp.id = :id")
    BladeProject findBladeProjectById(Long id);

}
