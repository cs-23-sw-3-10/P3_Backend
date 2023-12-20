package sw_10.p3_backend.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class ResourceOrderTest {

    private ResourceOrder resourceOrder;

    @BeforeEach
    public void setup() {
        resourceOrder = new ResourceOrder();
    }

    @Test
    public void cloneShouldReturnNewResourceOrderWithSameValues() throws CloneNotSupportedException {
        resourceOrder.setId(1);
        resourceOrder.setResourceType("Type");
        resourceOrder.setResourceName("Resource");
        resourceOrder.setWorkHours(10);
        resourceOrder.setEquipmentAssignmentStatus(Arrays.asList(true, false));
        resourceOrder.setBladeTask(new BladeTask());

        ResourceOrder clonedResourceOrder = resourceOrder.clone();

        assertNotSame(resourceOrder, clonedResourceOrder);
        assertNotEquals(resourceOrder.getId(), clonedResourceOrder.getId());
        assertEquals(resourceOrder.getResourceType(), clonedResourceOrder.getResourceType());
        assertEquals(resourceOrder.getResourceName(), clonedResourceOrder.getResourceName());
        assertEquals(resourceOrder.getWorkHours(), clonedResourceOrder.getWorkHours());
        assertNotSame(resourceOrder.getEquipmentAssignmentStatus(), clonedResourceOrder.getEquipmentAssignmentStatus());
        assertEquals(resourceOrder.getEquipmentAssignmentStatus(), clonedResourceOrder.getEquipmentAssignmentStatus());
        assertSame(resourceOrder.getBladeTask(), clonedResourceOrder.getBladeTask());
    }

    @Test
    public void cloneShouldReturnNewResourceOrderWithNullEntriesInEquipmentAssignmentStatusWhenOriginalHasNone() throws CloneNotSupportedException {
        resourceOrder.setId(1);
        resourceOrder.setResourceType("Type");
        resourceOrder.setResourceName("Resource");
        resourceOrder.setWorkHours(10);
        resourceOrder.setBladeTask(new BladeTask());

        ResourceOrder clonedResourceOrder = resourceOrder.clone();

        assertNotSame(resourceOrder, clonedResourceOrder);
        assertNotEquals(resourceOrder.getId(), clonedResourceOrder.getId());
        assertEquals(resourceOrder.getResourceType(), clonedResourceOrder.getResourceType());
        assertEquals(resourceOrder.getResourceName(), clonedResourceOrder.getResourceName());
        assertEquals(resourceOrder.getWorkHours(), clonedResourceOrder.getWorkHours());
        assertSame(resourceOrder.getBladeTask(), clonedResourceOrder.getBladeTask());
        assertArrayEquals(resourceOrder.getEquipmentAssignmentStatus().toArray(), clonedResourceOrder.getEquipmentAssignmentStatus().toArray());
    }
}
