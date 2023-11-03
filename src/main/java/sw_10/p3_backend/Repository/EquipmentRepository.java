package sw_10.p3_backend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    Equipment findByName(String name);
    Iterable<Equipment> findAllByType(String type);

    @Query("SELECT DISTINCT e.type FROM Equipment e")
    Iterable<String> findDistinctTypes();
}
