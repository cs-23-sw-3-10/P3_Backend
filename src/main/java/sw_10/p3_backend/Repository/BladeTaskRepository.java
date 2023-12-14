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

    /**
     * This query fetches all blade tasks that overlaps with a certain period and belongs to a certain schedule
     * @param start start of the overlapping period
     * @param end end of the overlapping period
     * @param isActive determines which schedule you fetch from
     * @return all blade tasks that overlap with the passed period and belongs to the passed schedule
     */
    @Query("SELECT bt FROM BladeTask bt WHERE (bt.bladeProject.schedule.isActive = :isActive) " +
            "AND (((bt.startDate > :start AND bt.startDate < :end) " +
            "OR (bt.endDate > :start AND bt.endDate < :end)) OR (bt.startDate < :start AND bt.endDate > :start))")
    List<BladeTask> bladeTasksInRange(LocalDate start, LocalDate end, boolean isActive);


    /**
     * This query fetches all blade tasks from a certain schedule that should lie in pending on the client
     * @param isActive determines which schedule you fetch from
     * @return all blade tasks that belongs to the passed schedule and should be placed in the pending
     */
    @Query("SELECT bt FROM BladeTask bt  WHERE (bt.bladeProject.schedule.isActive = :isActive) AND ((bt.startDate IS NULL) OR (bt.endDate IS NULL) OR (bt.testRig IS NULL))")
    List<BladeTask> bladeTasksPending(boolean isActive);

}