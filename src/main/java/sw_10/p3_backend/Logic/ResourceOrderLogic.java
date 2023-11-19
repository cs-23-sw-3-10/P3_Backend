package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.ResourceOrderInput;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceOrderLogic {
    public List<ResourceOrder> createResourceOrders(List<ResourceOrderInput> resourceOrders, BladeTask bladeTask) {

        List<ResourceOrder> resourceOrderList = new ArrayList<>();
        for (ResourceOrderInput resourceOrder: resourceOrders) {


            ResourceOrder newResourceOrder = new ResourceOrder(resourceOrder.type(), resourceOrder.amount(),
                    resourceOrder.equipmentAssignmentStatus(), resourceOrder.workHours(), bladeTask);
            bladeTask.addResourceOrder(newResourceOrder);

            resourceOrderList.add(newResourceOrder);
        }
        return resourceOrderList;
    }

}

