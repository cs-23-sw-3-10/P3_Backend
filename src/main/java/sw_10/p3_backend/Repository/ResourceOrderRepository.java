package sw_10.p3_backend.Repository;

import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.ResourceOrder;

import java.util.List;

@Repository
public interface ResourceOrderRepository extends JpaRepository<ResourceOrder,Long> {
    List<ResourceOrder> findAllByResourceName(String resourceName);
    List<ResourceOrder> findByBladeTask(BladeTask bladeTaskToUpdate);

    @Query("SELECT r FROM ResourceOrder r WHERE r.bladeProject.id = :id")
    List<ResourceOrder> findResourceOrderByBpId(Long id);
}
