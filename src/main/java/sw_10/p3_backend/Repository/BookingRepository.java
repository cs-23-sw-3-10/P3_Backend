package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

        @Query("SELECT e FROM Equipment e WHERE e.type = :type AND e NOT IN " +
                "(SELECT b.equipment FROM Booking b WHERE b.startDate < :end AND b.endDate > :start)")
        List<Equipment> findAvailableEquipment(LocalDate start, LocalDate end, String type);


        List<Booking> findByBladeTask(BladeTask bladeTaskToUpdate);

        //TODO: For conflict creation. Needs to return the bookings in a certain periode on a specific piece of equipment
        @Query("SELECT e FROM Booking e WHERE e.resourceName = :equipmentName AND e.startDate < :end AND e.endDate > :start")
        List<Booking> findBookedEquipmentByTypeAndPeriod(String equipmentName, LocalDate start, LocalDate end);

}
