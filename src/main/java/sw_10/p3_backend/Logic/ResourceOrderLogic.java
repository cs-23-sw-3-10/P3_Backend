package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.ResourceOrderInput;
import sw_10.p3_backend.Repository.ResourceOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceOrderLogic {
    private final ResourceOrderRepository resourceOrderRepository;
    public ResourceOrderLogic(ResourceOrderRepository resourceOrderRepository) {
        this.resourceOrderRepository = resourceOrderRepository;
    }

    /**
     * This method creates resourceOrders for a certain blade task from a list of resourceOrderInputs
     * @param resourceOrders the resourceOrderInput list
     * @param bladeTask the blade task the resourceOrders are made for
     * @return the list of resourceOrders that the method created
     */
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

    /**
     * This method creates resourceOrders for a certain blade project from a list of resourceOrderInputs
     * @param resourceOrders the resourceOrderInput list
     * @param bladeProject the blade project the resourceOrders are made for
     * @return the list of resourceOrders that the method created
     */
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


    /**
     * This method checks that the resourceOrderInput is valid and can be used to create a resourceOrder
     * @param resourceOrder the resourceOrderInput being validated
     */
    private void validateResourceOrder(ResourceOrderInput resourceOrder) {
        if(resourceOrder.resourceType() == null || resourceOrder.resourceName() == null || resourceOrder.equipmentAssignmentStatus() == null){
            throw new IllegalArgumentException("ResourceOrder is missing required fields");
        }
    }

    /**
     * This method finds all resourceOrders with a certain resource name
     * @param resourceOrder the resource name
     * @return the list of resourceOrders with the resource name
     */
    public List<ResourceOrder> findResourceName(String resourceOrder) {
        return resourceOrderRepository.findAllByResourceName(resourceOrder);
    }

    /**
     * This method finds all resourceOrders that belong to a certain blade task
     * @param bladeTask the blade task that is being updated
     */
    public void removeResourceOrdersBladeTask(BladeTask bladeTask) {
        //Finds the bladetasks resourceorders
        List<ResourceOrder> resourceOrders = resourceOrderRepository.findByBladeTask(bladeTask);

        //Deletes the resource orders
        resourceOrderRepository.deleteAll(resourceOrders);
    }

    /**
     * This method finds all resourceOrders that belong to a certain blade project
     * @param BPId the id blade project that is being updated
     */
    public void removeResourceOrdersBladeProject(Long BPId) {
        //Finds Blade Project resource orders
        List<ResourceOrder> resourceOrders = resourceOrderRepository.findResourceOrderByBpId(BPId);

        //Deletes the resource orders
        resourceOrderRepository.deleteAll(resourceOrders);
    }

    /**
     * This method finds all resourceOrders that belong to a certain blade project
     * @param id
     * @return the list of resourceOrders that belong to the blade project with the passed id
     */
    public List<ResourceOrder> findResourceOrderByBpId(Long id){
        return resourceOrderRepository.findResourceOrderByBpId(id);
    }
}

