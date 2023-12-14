package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Conflict;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BladeTaskRepository extends JpaRepository<BladeTask,Long> {


    @Query("SELECT bt FROM BladeTask bt WHERE (bt.bladeProject.schedule.isActive = :isActive) " +
            "AND (((bt.startDate > :start AND bt.startDate < :end) " +
            "OR (bt.endDate > :start AND bt.endDate < :end)) OR (bt.startDate <= :start AND bt.endDate > :start))")
    List<BladeTask> bladeTasksInRange(LocalDate start, LocalDate end, boolean isActive);


    BladeTask getBladeTaskById(int id);


    @Query("SELECT bt FROM BladeTask bt  WHERE (bt.bladeProject.schedule.isActive = :isActive) AND ((bt.startDate IS NULL) OR (bt.endDate IS NULL) OR (bt.testRig IS NULL))")
    List<BladeTask> bladeTasksPending(boolean isActive);

}