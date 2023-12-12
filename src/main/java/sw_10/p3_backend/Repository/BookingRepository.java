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

        /**
         * This query fetches all bookings that belong to a certain blade task
         * @param bladeTaskToUpdate
         * @return all the bookings that belong to the passed blade task
         */
        List<Booking> findByBladeTask(BladeTask bladeTaskToUpdate);

        /**
         *This query fetches all bookings with a certain resourceName and that overlaps with the period and is in the edit schedule
         * @param equipmentName the name of the resource. This can be "adapter a" for equipment, "smith" for technician and "rodney johnson"
         * @param start the start of the overlapping period
         * @param end the end of the overlapping period
         * @return all bookings that is bookings with the passed resourceName, overlaps with the passed periods and belongs to the edit schedule
         */
        @Query("SELECT e FROM Booking e WHERE e.resourceName = :equipmentName AND e.startDate < :end AND e.endDate > :start And e.bladeTask.bladeProject.schedule.isActive = false")
        List<Booking> findBookedEquipmentByTypeAndPeriod(String equipmentName, LocalDate start, LocalDate end);

        /**
         * This query fetches all equipment bookings that belong to the edit schedule and overlap the period
         * @param start the start of the overlapping period
         * @param end the end of the overlapping period
         * @return all equipment bookings that belong to the edit schedule and overlap the passed period
         */
        @Query("SELECT b FROM Booking b WHERE b.startDate < :end AND b.endDate > :start AND b.resourceType = 'Equipment' AND b.bladeTask.bladeProject.schedule.isActive = false")
        List<Booking> findAllByPeriod(LocalDate start, LocalDate end);

        /**
         * This query fetches all bookings that belong to the edit schedule and a certain blade project
         * @param id id of the blade project
         * @return all the bookings that belong to the blade project with the passed id and the edit schedule
         */
        @Query("SELECT b FROM Booking b WHERE b.bladeProject.id = :id AND b.bladeProject.schedule.isActive = false")
        List<Booking> findBookingsByBPId(Long id);
}
