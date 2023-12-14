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

    /**
     * This query fetches all conflicts that belong to a certain booking
     * @param booking the booking you want to get conflicts for
     * @return all bookings that belong to the booking
     */
    List<Conflict> findAllByBooking(Booking booking);

    /**
     * This query fetches the conflict that belongs to a certain booking and belongs to either the edit schedule or view schedule
     * @param bookingId
     * @param isActive determines which schedule it belongs to
     * @return the conflict that belongs to the passed booking and the passed schedule
     */
    @Query("SELECT conflict FROM Conflict conflict WHERE (conflict.booking.id = :bookingId ) AND (conflict.booking.bladeTask.bladeProject.schedule.isActive = :isActive) ")
    Conflict findConflictByBookingId( Integer bookingId, boolean isActive);

}
