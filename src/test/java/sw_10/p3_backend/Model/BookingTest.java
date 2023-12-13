package sw_10.p3_backend.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void shouldCalculateDurationForBladeTaskTechnicianBooking() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        Technician technician = new Technician();
        BladeTask bladeTask = new BladeTask();
        Booking booking = new Booking(startDate, endDate, technician, bladeTask, "resourceType", "resourceName");

        assertEquals(4, booking.getDuration());
    }

    @Test
    void shouldCalculateDurationForBladeTaskEngineerBooking() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        Engineer engineer = new Engineer();
        BladeTask bladeTask = new BladeTask();
        Booking booking = new Booking(startDate, endDate, engineer, bladeTask, "resourceType", "resourceName");

        assertEquals(4, booking.getDuration());
    }

    @Test
    void shouldCalculateDurationForBladeProjectEngineerBooking() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        Engineer engineer = new Engineer();
        BladeProject bladeProject = new BladeProject();
        Booking booking = new Booking(startDate, endDate, engineer, bladeProject, "resourceType", "resourceName");

        assertEquals(4, booking.getDuration());
    }

    @Test
    void shouldCloneBooking() throws CloneNotSupportedException {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        Engineer engineer = new Engineer();
        BladeProject bladeProject = new BladeProject();
        Booking booking = new Booking(startDate, endDate, engineer, bladeProject, "resourceType", "resourceName");
        Booking clonedBooking = booking.clone();

        assertEquals(booking.getStartDate(), clonedBooking.getStartDate());
        assertEquals(booking.getEndDate(), clonedBooking.getEndDate());
        assertEquals(booking.getResourceType(), clonedBooking.getResourceType());
        assertEquals(booking.getResourceName(), clonedBooking.getResourceName());
        assertEquals(0, clonedBooking.getId());
    }

    @Test
    void shouldFetchBladeTask() {
        BladeTask bladeTask = new BladeTask();
        Booking booking = new Booking(LocalDate.now(), LocalDate.now(), bladeTask, "resourceType", "resourceName");

        assertEquals(bladeTask, booking.fetchBladeTask());
    }

    @Test
    void shouldFetchEquipment() {
        Equipment equipment = new Equipment();
        Booking booking = new Booking(LocalDate.now(), LocalDate.now(), equipment, new BladeProject(), "resourceType", "resourceName");

        assertEquals(equipment, booking.fetchEquipment());
    }
}