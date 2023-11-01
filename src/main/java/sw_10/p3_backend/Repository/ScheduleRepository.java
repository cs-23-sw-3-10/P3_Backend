package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
