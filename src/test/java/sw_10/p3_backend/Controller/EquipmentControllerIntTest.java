package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Logic.TokenLogic;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
@DirtiesContext
public class EquipmentControllerIntTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TokenLogic tokenLogic;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    BookingRepository bookingRepository;

    Authentication authentication;

    @BeforeEach
    public void clearRepository() {
        equipmentRepository.deleteAll();
    }

    @Test
    public void testFindAllEquipmentShouldReturnAllEquipment(){
        //Arrange
        authentication = tokenLogic.authenticate("user", "password");

        equipmentRepository.save(new Equipment());
        equipmentRepository.save(new Equipment());
        equipmentRepository.save(new Equipment());

        String token = tokenLogic.generateToken(authentication);

        String document = """
                query {
                  AllEquipment {
                    id
                                      }
                }
                """;
        List<Equipment> technicianList = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(document).execute().errors().verify().path("AllEquipment").entityList(Equipment.class).get();

        assertThat(technicianList.size()).isEqualTo(3);
    }

    @Test
    public void testFindEquipmentByIdShouldReturnEquipment(){
        //Arrange
        authentication = tokenLogic.authenticate("user", "password");

        Equipment equipment = new Equipment();

        equipment.setName("IdTest");

        Equipment savedEquipment = equipmentRepository.save(equipment);

        int id = savedEquipment.getId();

        String token = tokenLogic.generateToken(authentication);

        String query = String.format("""
                query {
                  EquipmentById(id: %d) {
                    id
                    name
                    }
                }
                """, id);
        Equipment equipment1 = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("EquipmentById").entity(Equipment.class).get();

        assertThat(equipment1.getId()).isEqualTo(id);
        assertThat(equipment1.getName()).isEqualTo("IdTest");
    }

    @Test
    public void testCreateEquipmentShouldReturnEquipment(){
        //Arrange
        authentication = tokenLogic.authenticate("user", "password");

        String token = tokenLogic.generateToken(authentication);

        String calibrationExpirationDate = LocalDate.now().plusDays(1).toString();

        String query = String.format("""
                mutation {
                  CreateEquipment(name: "Test", type: "Test", calibrationExpirationDate: "%s") {
                    id
                    name
                    }
                }
                """, calibrationExpirationDate);
        Equipment equipment = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("CreateEquipment").entity(Equipment.class).get();

        assertThat(equipment.getId()).isEqualTo(1);
        assertThat(equipment.getName()).isEqualTo("Test");
    }

    @Test
    public void testDeleteEquipmentShouldReturnEquipment(){
        //Arrange
        authentication = tokenLogic.authenticate("user", "password");

        Equipment equipment = new Equipment();

        equipment.setName("DeleteTest");

        Equipment savedEquipment = equipmentRepository.save(equipment);

        int id = savedEquipment.getId();

        String token = tokenLogic.generateToken(authentication);

        String query = String.format("""
                mutation {
                  DeleteEquipment(name: "DeleteTest") {
                    id
                    name
                    }
                }
                """);
        Equipment equipment1 = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("DeleteEquipment").entity(Equipment.class).get();

        assertThat(equipment1.getId()).isEqualTo(id);
        assertThat(equipment1.getName()).isEqualTo("DeleteTest");
        assertThat(equipmentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void testDeleteEquipmentWithAssociatedBookings(){
        //Arrange
        authentication = tokenLogic.authenticate("user", "password");

        Equipment equipment = new Equipment();

        equipment.setName("DeleteTest");

        Equipment savedEquipment = equipmentRepository.save(equipment);

        Booking booking = new Booking();

        booking.setEquipment(savedEquipment);

        bookingRepository.save(booking);

        int id = savedEquipment.getId();



        String token = tokenLogic.generateToken(authentication);

        String query = String.format("""
                mutation {
                  DeleteEquipment(name: "DeleteTest") {
                    id
                    name
                    }
                }
                """);
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("DeleteEquipment").entity(Equipment.class).get();
        });

        String expectedMessage = "Cannot delete equipment that is assigned to a task.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}