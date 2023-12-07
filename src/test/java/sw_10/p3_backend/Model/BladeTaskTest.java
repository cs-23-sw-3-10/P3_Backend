package sw_10.p3_backend.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BladeTaskTest {

    @Test
    void shouldCreateBladeTaskWithStartDateAndDuration() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        int duration = 5;
        BladeProject bladeProject = new BladeProject();
        BladeTask bladeTask = new BladeTask(startDate, duration, bladeProject);

        assertEquals(startDate, bladeTask.getStartDate());
        assertEquals(duration, bladeTask.getDuration());
        assertEquals(bladeProject, bladeTask.getBladeProject());
    }

    @Test
    void shouldCreateBladeTaskWithAllParameters() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        int duration = 5;
        String testType = "TypeA";
        int attachPeriod = 2;
        int detachPeriod = 3;
        String taskName = "TaskA";
        int testRig = 1;
        BladeProject bladeProject = new BladeProject();
        BladeTask bladeTask = new BladeTask(startDate, endDate, duration, testType, attachPeriod, detachPeriod, taskName, testRig, bladeProject);

        assertEquals(startDate, bladeTask.getStartDate());
        assertEquals(endDate, bladeTask.getEndDate());
        assertEquals(duration, bladeTask.getDuration());
        assertEquals(testType, bladeTask.getTestType());
        assertEquals(attachPeriod, bladeTask.getAttachPeriod());
        assertEquals(detachPeriod, bladeTask.getDetachPeriod());
        assertEquals(taskName, bladeTask.getTaskName());
        assertEquals(testRig, bladeTask.getTestRig());
        assertEquals(bladeProject, bladeTask.getBladeProject());
    }

    @Test
    void shouldCloneBladeTask() throws CloneNotSupportedException {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        int duration = 5;
        BladeProject bladeProject = new BladeProject();
        BladeTask bladeTask = new BladeTask(startDate, duration, bladeProject);
        BladeTask clonedBladeTask = bladeTask.clone();

        assertEquals(bladeTask.getStartDate(), clonedBladeTask.getStartDate());
        assertEquals(bladeTask.getDuration(), clonedBladeTask.getDuration());
        assertEquals(bladeTask.getBladeProject(), clonedBladeTask.getBladeProject());
        assertEquals(0, clonedBladeTask.getId());
    }

    @Test
    void shouldAddResourceOrderToBladeTask() {
        BladeTask bladeTask = new BladeTask();
        ResourceOrder resourceOrder = new ResourceOrder();
        bladeTask.addResourceOrder(resourceOrder);

        assertTrue(bladeTask.getResourceOrders().contains(resourceOrder));
        assertEquals(bladeTask, resourceOrder.getBladeTask());
    }

    @Test
    void shouldAddAndRemoveRelatedConflict() {
        BladeTask bladeTask = new BladeTask();
        Conflict conflict = new Conflict();
        bladeTask.addRelatedConflict(conflict);

        assertTrue(bladeTask.getRelatedConflicts().contains(conflict));

        bladeTask.removeRelatedConflict(conflict);
        assertFalse(bladeTask.getRelatedConflicts().contains(conflict));
    }
}