package sw_10.p3_backend.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Schedule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;

    @BeforeEach
    public void setup() {
        schedule = new Schedule();
    }

    @Test
    public void cloneShouldReturnNewScheduleWithSameValuesWhenActive() throws CloneNotSupportedException {
        schedule.setId(1);
        schedule.setActive(true);
        List<BladeProject> bladeProjects = new ArrayList<>();
        bladeProjects.add(new BladeProject());
        schedule.setBladeProject(bladeProjects);

        Schedule clonedSchedule = (Schedule) schedule.clone(true);

        assertNotSame(schedule, clonedSchedule);
        assertEquals(1, clonedSchedule.getId());
        assertEquals(schedule.isActive(), clonedSchedule.isActive());
        assertNotSame(schedule.getBladeProject(), clonedSchedule.getBladeProject());
        assertEquals(schedule.getBladeProject().size(), clonedSchedule.getBladeProject().size());
    }

    @Test
    public void cloneShouldReturnNewScheduleWithDifferentIdWhenNotActive() throws CloneNotSupportedException {
        schedule.setId(1);
        schedule.setActive(true);
        List<BladeProject> bladeProjects = new ArrayList<>();
        bladeProjects.add(new BladeProject());
        schedule.setBladeProject(bladeProjects);

        Schedule clonedSchedule = (Schedule) schedule.clone(false);

        assertNotSame(schedule, clonedSchedule);
        assertEquals(2, clonedSchedule.getId());
        assertEquals(schedule.isActive(), clonedSchedule.isActive());
        assertNotSame(schedule.getBladeProject(), clonedSchedule.getBladeProject());
        assertEquals(schedule.getBladeProject().size(), clonedSchedule.getBladeProject().size());
    }

    @Test
    public void cloneShouldReturnNewScheduleWithEmptyBladeProjectsWhenOriginalHasNone() throws CloneNotSupportedException {
        schedule.setId(1);
        schedule.setActive(true);

        Schedule clonedSchedule = (Schedule) schedule.clone(true);

        assertNotSame(schedule, clonedSchedule);
        assertEquals(1, clonedSchedule.getId());
        assertEquals(schedule.isActive(), clonedSchedule.isActive());
        assertTrue(clonedSchedule.getBladeProject().isEmpty());
    }
}
