package sw_10.p3_backend.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;
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


    @Test
    void testFindOverlappingEventsShouldReturnOverlappingBookings(){
        Equipment e1 = new Equipment(1, "hammer", LocalDate.of(2000,10,10), "hammer1", null);
        Equipment e2 = new Equipment(2, "saw", LocalDate.of(2000,10,10), "hammer1", null);

        equipmentRepository.save(e1);
        equipmentRepository.save(e2);

        Booking booking1 = new Booking(LocalDate.of(2020,10,1), LocalDate.of(2020,10,10), e1, (BladeTask) null, e1.getType() ,e1.getName());
        Booking booking2 = new Booking(LocalDate.of(2020,10,1), LocalDate.of(2020,10,10), e1, (BladeTask) null, e1.getType() ,e1.getName());
        Booking booking3 = new Booking(LocalDate.of(2020,10,1), LocalDate.of(2020,10,10), e1, (BladeTask) null, e1.getType() ,e1.getName());


        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);

        List<Booking> overlappingBookings = bookingRepository.findBookedEquipmentByTypeAndPeriod("hammer", LocalDate.of(2020,10,1),LocalDate.of(2020,10,20));


        List<Booking> overlappingBookingsDifferentType = bookingRepository.findBookedEquipmentByTypeAndPeriod("saw", LocalDate.of(2020,10,1),LocalDate.of(2020,10,30));
        assertThat(overlappingBookingsDifferentType).isEmpty();
    }


}