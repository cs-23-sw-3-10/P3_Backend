package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BladeTaskRepository extends JpaRepository<BladeTask,Long> {


    @Query("SELECT bt FROM BladeTask bt  WHERE (bt.startDate > :start AND bt.startDate < :end) " +
            "OR (bt.endDate > :start AND bt.endDate < :end )")
    List<BladeTask> bladeTasksInRange(LocalDate start, LocalDate end);

}
