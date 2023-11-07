package sw_10.p3_backend.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


// Use Testcontainers to create a real PostgreSQL database container instead of mockup
// DataJpaTest is used for JPA related test configuration
// AutoConfigureTestDatabase is used to avoid replacing the application's default datasource(container instead of production db)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingRepositoryTest {

    // This container starts a PostgreSQL database container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    @Test
    void testFindOverlappingEventsShouldReturnOverlappingBookings(){


        Equipment e1 = new Equipment(1, "hammer", new Date(11), "hammer1", null);
        Equipment e2 = new Equipment(2, "saw", new Date(11), "hammer1", null);

        equipmentRepository.save(e1);
        equipmentRepository.save(e2);

        Booking booking1 = new Booking(1,new Date(2020-10-1), new Date(2020-10-10),10, "hammer", 0, null,null,null,e1);
        Booking booking2 = new Booking(2,new Date(2020-10-11), new Date(2020-10-20),10, "hammer", 0, null,null,null,e1);
        Booking booking3 = new Booking(3,new Date(2020-10-21), new Date(2020-10-30),10, "hammer", 0, null,null,null,e2);

        System.out.println(new Date(2020-10-10));

        // Save bookings to the repository
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);

        // When
        // Check for overlapping events for same type
        List<Booking> overlappingBookings = bookingRepository.findOverlappingEvents(new Date(2020-10-1),new Date(2020-10-20),"hammer");
        // Then
        // Assert the overlapping bookings are found and non-overlapping are not
        List<Integer> overlappingIds = overlappingBookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        assertThat(overlappingIds).containsExactlyInAnyOrder(booking1.getId(), booking2.getId());
        assertThat(overlappingIds).doesNotContain(booking3.getId());

        List<Booking> overlappingBookingsDifferentType = bookingRepository.findOverlappingEvents(new Date(2020-10-1),new Date(2020-10-30),"saw");
        assertThat(overlappingBookingsDifferentType).hasSize(1);
    }


}