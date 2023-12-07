package sw_10.p3_backend.Repository;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.*;

import sw_10.p3_backend.TestP3BackendApplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// ActiveProfiles specify that application-test.properties will be used for database config(In order to not inject data from data.sql)
// Use Testcontainers to create a real PostgreSQL database container instead of mockup
// DataJpaTest is used for JPA related test configuration
// AutoConfigureTestDatabase is used to avoid replacing the application's default datasource(container instead of production db)

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestP3BackendApplication.class})
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    BladeProjectRepository bladeProjectRepository;
    @Autowired
    BladeTaskRepository bladeTaskRepository;



    @Test
    void testFindOverlappingEventsShouldReturnOverlappingBookings(){

        // Given
        Schedule schedule = new Schedule();
        schedule.setActive(false);
        scheduleRepository.save(schedule);

        BladeProject bladeProject = new BladeProject(schedule, "test", "test", "test", "test");

        bladeProjectRepository.save(bladeProject);


        BladeTask bladeTask = new BladeTask(LocalDate.of(2020,10,1), 30, bladeProject);

        bladeTaskRepository.save(bladeTask);

        Equipment e1 = new Equipment(1, "hammer", LocalDate.of(2000,10,10), "hammer1", null);
        Equipment e2 = new Equipment(2, "saw", LocalDate.of(2000,10,10), "hammer1", null);

        equipmentRepository.save(e1);
        equipmentRepository.save(e2);


        Booking booking1 = new Booking(LocalDate.of(2020,10,1), LocalDate.of(2020,10,10), e1, bladeTask, e1.getType(), e1.getName());
        Booking booking2 = new Booking(LocalDate.of(2020,10,20), LocalDate.of(2020,10,30), e1, bladeTask, e1.getType(), e1.getName());
        Booking booking3 = new Booking(LocalDate.of(2020,10,1), LocalDate.of(2020,10,30), e2, bladeTask, e2.getType(), e2.getName());

        System.out.println("look here" + booking1.fetchBladeTask());

        Schedule test33 = scheduleRepository.findScheduleByIsActive(false);

        System.out.println(test33);
        // Save bookings to the repository

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);


        // When
        // Check for overlapping events for same type
        List<Booking> bookingsByTypeHammerAndPeriod = bookingRepository.findAll();
        // Then

        System.out.println(bookingsByTypeHammerAndPeriod);

        System.out.println("BP tasks: " + bladeProject.getBladeTasks().size());

        // Assert the overlapping bookings are found and non-overlapping are not
        assertThat(bookingsByTypeHammerAndPeriod).containsExactlyInAnyOrder(booking1, booking2, booking3);

        List<Booking> bookingsByTypeSawAndPeriod = bookingRepository.findByBladeTask(bladeTaskRepository.getBladeTaskById(1));
        assertThat(bookingsByTypeSawAndPeriod).hasSize(1);

    }


}