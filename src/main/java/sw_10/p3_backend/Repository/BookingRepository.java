package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Booking;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

        @Query("SELECT e FROM Booking e WHERE e.startDate < :end AND e.endDate > :start AND e.equipment.type = :type " )
        List<Booking> findOverlappingEvents(Date start, Date end, String type);


}
