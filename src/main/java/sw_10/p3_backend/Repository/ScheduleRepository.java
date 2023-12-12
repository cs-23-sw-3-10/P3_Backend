package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    /**
     * This query fetches either the view schedule or edit schedule
     * @param active determines whether it fetches the view schedule (true) or the edit schedule (false)
     * @return either the view schedule or edit schedule
     */
    Schedule findScheduleByIsActive(boolean active);
}
