package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeProject;

import java.util.List;

@Repository
public interface BladeProjectRepository extends JpaRepository<BladeProject,Long> {
@Query("SELECT bp FROM BladeProject bp WHERE bp.schedule.isActive = :isActive")
List<BladeProject> findAllBySchedule(boolean isActive);

@Query("SELECT bp FROM BladeProject bp WHERE bp.id = :id")
BladeProject findBladeProjectById(Long id);

//for testing purpose
@Query("SELECT bp FROM BladeProject bp LEFT JOIN FETCH bp.bladeTasks WHERE bp.id = :id")
BladeProject findByIdWithBladeTasks(Long id);

}
