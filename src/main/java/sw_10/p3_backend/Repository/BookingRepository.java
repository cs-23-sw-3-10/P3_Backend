package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
}
