package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Equipment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    Equipment findByName(String name);
    List<Equipment> findAllByType(String type);

    @Query("SELECT e FROM Equipment e WHERE e.type = :type AND e NOT IN " +
            "(SELECT b.equipment FROM Booking b WHERE b.startDate < :end AND b.endDate > :start)")
    List<Equipment> findAvailableEquipment(LocalDate start, LocalDate end, String type);


}
