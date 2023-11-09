package sw_10.p3_backend.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.TestP3BackendApplication;

// ActiveProfiles specify that application-test.properties will be used for database config(In order to not inject data from data.sql)
// Use Testcontainers to create a real PostgreSQL database container instead of mockup
// DataJpaTest is used for JPA related test configuration
// ContextConfiguration is used to specify what container should be used
// AutoConfigureTestDatabase is used to avoid replacing the application's default datasource(container instead of production db)

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestP3BackendApplication.class})
class TechnicianRepositoryTest {

    @Autowired
    TechnicianRepository technicianRepository;

    @Test
    void testFindByIdShouldReturnTechnician(){

        System.out.println("Number of techs" + technicianRepository.findAll());

        technicianRepository.save(new Technician(1,"smith", 20, 37, 3, null));

        assertThat(technicianRepository.findByType("smith").getType()).isEqualTo("smith");
    }
}