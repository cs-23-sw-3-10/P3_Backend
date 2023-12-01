package sw_10.p3_backend.Repository;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConflictRepository extends JpaRepository<Conflict,Long> {
    List<Conflict> findAllByBooking(Booking booking);


    @Query("SELECT conflict FROM Conflict conflict WHERE (conflict.booking.id = :bookingId ) AND (conflict.booking.bladeTask.bladeProject.schedule.isActive = :isActive) ")
    Conflict findConflictByBookingId( Integer bookingId, boolean isActive);

}
