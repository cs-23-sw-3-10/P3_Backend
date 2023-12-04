package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.ResourceOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceOrderLogic {
    private final ResourceOrderRepository resourceOrderRepository;
    public ResourceOrderLogic(ResourceOrderRepository resourceOrderRepository) {
        this.resourceOrderRepository = resourceOrderRepository;
    }
    public List<ResourceOrder> createResourceOrders(List<ResourceOrderInput> resourceOrders, BladeTask bladeTask) {


        List<ResourceOrder> resourceOrderList = new ArrayList<>();
        for (ResourceOrderInput resourceOrder: resourceOrders) {

            int workHours = 0;
            //Validate resourceOrder have required fields
            //TODO: Find better solution for this
            validateResourceOrder(resourceOrder);
            if(resourceOrder.workHours() != null){
                workHours = resourceOrder.workHours();
            }

            //Create new resourceOrder
            ResourceOrder newResourceOrder = new ResourceOrder(resourceOrder.resourceType(), resourceOrder.resourceName(),
                    resourceOrder.equipmentAssignmentStatus(), workHours, bladeTask);
            bladeTask.addResourceOrder(newResourceOrder);

            resourceOrderList.add(newResourceOrder);
        }
        return resourceOrderList;
    }

    private void validateResourceOrder(ResourceOrderInput resourceOrder) {
        if(resourceOrder.resourceType() == null || resourceOrder.resourceName() == null || resourceOrder.equipmentAssignmentStatus() == null){
            throw new IllegalArgumentException("ResourceOrder is missing required fields");
        }
    }
    public List<ResourceOrder> findResourceName(String resourceOrder) {
        return resourceOrderRepository.findAllByResourceName(resourceOrder);
    }

    public void removeResourceOrders(BladeTask bladeTaskToUpdate) {
        //Finds the bladetasks resourceorders
        List<ResourceOrder> resourceOrders = resourceOrderRepository.findByBladeTask(bladeTaskToUpdate);

        //Deletes the resourceorders
        resourceOrderRepository.deleteAll(resourceOrders);
    }
}

