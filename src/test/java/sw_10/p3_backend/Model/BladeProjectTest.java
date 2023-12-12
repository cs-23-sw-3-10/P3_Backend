package sw_10.p3_backend.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BladeProjectTest {

    @Test
    void shouldCreateBladeProjectWithBasicConstructor() {
        String projectName = "ProjectA";
        BladeProject bladeProject = new BladeProject(projectName);

        assertEquals(projectName, bladeProject.getProjectName());
    }

    @Test
    void shouldCreateBladeProjectWithFullConstructor() {
        Schedule schedule = new Schedule();
        String projectName = "ProjectA";
        String customer = "CustomerA";
        String projectLeader = "LeaderA";
        String color = "ColorA";
        BladeProject bladeProject = new BladeProject(schedule, projectName, customer, projectLeader, color);

        assertEquals(schedule, bladeProject.fetchSchedule());
        assertEquals(projectName, bladeProject.getProjectName());
        assertEquals(customer, bladeProject.getCustomer());
        assertEquals(projectLeader, bladeProject.getProjectLeader());
        assertEquals(color, bladeProject.getColor());
    }

    @Test
    void shouldCloneBladeProjectWithSchedule() {
        Schedule schedule = new Schedule();
        String projectName = "ProjectA";
        BladeProject bladeProject = new BladeProject(projectName);
        BladeProject clonedBladeProject = bladeProject.cloneWithSchedule(schedule);

        assertEquals(schedule, clonedBladeProject.fetchSchedule());
        assertEquals(projectName, clonedBladeProject.getProjectName());
        assertTrue(clonedBladeProject.getBladeTasks().isEmpty());
        assertTrue(clonedBladeProject.getResourceOrders().isEmpty());
        assertTrue(clonedBladeProject.getBookings().isEmpty());
    }

    @Test
    void shouldAddResourceOrderToBladeProject() {
        BladeProject bladeProject = new BladeProject();
        ResourceOrder resourceOrder = new ResourceOrder();
        bladeProject.addResourceOrder(resourceOrder);

        assertTrue(bladeProject.getResourceOrders().contains(resourceOrder));
        assertEquals(bladeProject, resourceOrder.fetchBladeProject());
    }
}