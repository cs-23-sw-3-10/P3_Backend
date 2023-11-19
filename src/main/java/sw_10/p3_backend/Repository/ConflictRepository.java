package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;

import java.util.List;

@Repository
public interface ConflictRepository extends JpaRepository<Conflict,Long> {
    List<Conflict> findAllByBooking(Booking booking);
}
