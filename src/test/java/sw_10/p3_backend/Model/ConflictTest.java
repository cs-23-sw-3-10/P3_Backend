package sw_10.p3_backend.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConflictTest {

    private Conflict conflict;

    @BeforeEach
    public void setup() {
        conflict = new Conflict();
    }

    @Test
    public void cloneShouldReturnNewConflictWithSameValues() throws CloneNotSupportedException {
        conflict.setId(1);
        conflict.setType("Type");
        conflict.setMessage("Message");
        conflict.setBooking(new Booking());
        conflict.setSchedule(new Schedule());
        Set<BladeTask> relatedBladeTasks = new HashSet<>();
        relatedBladeTasks.add(new BladeTask());
        conflict.setRelatedBladeTasks(relatedBladeTasks);

        Conflict clonedConflict = conflict.clone(new Booking());

        assertNotSame(conflict, clonedConflict);
        assertEquals(0, clonedConflict.getId());
        assertEquals(conflict.getType(), clonedConflict.getType());
        assertEquals(conflict.getMessage(), clonedConflict.getMessage());
        assertNotSame(conflict.getBooking(), clonedConflict.getBooking());
        assertSame(conflict.getSchedule(), clonedConflict.getSchedule());
        assertNotSame(conflict.getRelatedBladeTasks(), clonedConflict.getRelatedBladeTasks());
        assertTrue(clonedConflict.getRelatedBladeTasks().isEmpty());
    }

    @Test
    public void constructorShouldSetValuesCorrectly() {
        Booking booking = new Booking();
        booking.setResourceType("Type");
        booking.setResourceName("Resource");
        booking.setStartDate(LocalDate.parse("2021-01-01"));
        booking.setEndDate(LocalDate.parse("2021-01-31"));

        BladeTask bladeTask = new BladeTask();
        bladeTask.setTaskName("Task");

        Set<BladeTask> hashedBladeTasks = new HashSet<>();
        hashedBladeTasks.add(bladeTask);

        Conflict conflict = new Conflict(booking, bladeTask, hashedBladeTasks);

        assertEquals("Type", conflict.getType());
        assertSame(booking, conflict.fetchBooking());
        assertTrue(conflict.getMessage().contains("Conflict!"));
        assertTrue(conflict.getMessage().contains("Booking of equipment: Resource"));
        assertTrue(conflict.getMessage().contains("in period 2021-01-01 - 2021-01-31 for Task"));
        assertTrue(conflict.getMessage().contains("was not possible due to lack of resources."));
        assertTrue(conflict.getMessage().contains("Bladetasks: Task has bookings of this equipment in this period."));
        assertSame(hashedBladeTasks, conflict.getRelatedBladeTasks());
    }
}