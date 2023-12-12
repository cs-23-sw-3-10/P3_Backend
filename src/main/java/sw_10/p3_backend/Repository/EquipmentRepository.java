package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Equipment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

    /**
     * This query fetches the equipment with a certain name
     * @param name name of the equipment
     * @return the equipment with the passed name
     */
    Equipment findByName(String name);

    /**
     * This query fetches all equipment of a certain type
     * @param type the type of equipment you wish to fetch
     * @return all the equipment of the passed type
     */
    List<Equipment> findAllByType(String type);

    /**
     * This query fetches all equipment that has a certain type and does not have a booking in the period
     * @param start start of the period
     * @param end end of the period
     * @param type the type of equipment you wish to fetch
     * @return a list of all equipment that has belongs to the passed type and does not have a booking in the passed period
     */
    @Query("SELECT e FROM Equipment e WHERE e.type = :type AND e NOT IN " +
            "(SELECT b.equipment FROM Booking b WHERE b.bladeTask.bladeProject.schedule.isActive = false  AND b.startDate <= :end AND b.endDate >= :start AND b.equipment is not null)")
    List<Equipment> findAvailableEquipment(LocalDate start, LocalDate end, String type);

    /**
     * This query fetches a list of unique types from the database
     * @return a list of unique equipment
     */
    @Query("SELECT DISTINCT e.type FROM Equipment e")
    List<String> getEquipmentTypes();
}
