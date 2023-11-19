package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Model.Technician;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

}
