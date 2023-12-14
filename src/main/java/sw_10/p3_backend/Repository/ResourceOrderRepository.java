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

    /**
     * This query fetches all resourceOrders with resource of a certain name
     * @param resourceName the name of the resource you want to fetch resource orders for
     * @return all resourceOrders that has teh passed resourceName
     */
    List<ResourceOrder> findAllByResourceName(String resourceName);

    /**
     * This query fetches all resourceOrders belonging to a certain blade task
     * @param bladeTaskToUpdate
     * @return all resourceOrders that belong to the passed blade task
     */
    List<ResourceOrder> findByBladeTask(BladeTask bladeTaskToUpdate);

    /**
     * This query fetches all resourceOrders belonging to a certain blade project and belongs to the edit schedule
     * @param id
     * @return all resourceOrders that belong to the passed blade project and belongs to the edit schedule
     */
    @Query("SELECT r FROM ResourceOrder r WHERE r.bladeProject.id = :id AND r.bladeProject.schedule.isActive = false")
    List<ResourceOrder> findResourceOrderByBpId(Long id);
}
