package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.ResourceOrderInput;
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
    public List<ResourceOrder> createResourceOrdersBladeTask(List<ResourceOrderInput> resourceOrders, BladeTask bladeTask) {
        List<ResourceOrder> resourceOrderList = new ArrayList<>();
        for (ResourceOrderInput resourceOrder: resourceOrders) {

            int workHours = 0;
            //Validate resourceOrder have required fields
            //TODO: Find better solution for this
            validateResourceOrder(resourceOrder);

            //Check is made since it can be either equipment or employee
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
    public List<ResourceOrder> createResourceOrdersBladeProject(List<ResourceOrderInput> resourceOrders, BladeProject bladeProject) {
        List<ResourceOrder> resourceOrderList = new ArrayList<>();

        for (ResourceOrderInput resourceOrder: resourceOrders) {
            int workHours = 0;
            validateResourceOrder(resourceOrder);

            if(resourceOrder.workHours() != null){
                workHours = resourceOrder.workHours();
            }

            //Create new resourceOrder
            ResourceOrder newResourceOrder = new ResourceOrder(resourceOrder.resourceType(), resourceOrder.resourceName(),
                    resourceOrder.equipmentAssignmentStatus(), workHours, bladeProject);
            bladeProject.addResourceOrder(newResourceOrder);
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
    public List<ResourceOrder> findResourceOrderByBpId(Long id){
        return resourceOrderRepository.findResourceOrderByBpId(id);
    }
}

