package sw_10.p3_backend.Repository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.TestP3BackendApplication;

// ActiveProfiles specify that application-test.properties will be used for database config(In order to not inject data from data.sql)
// Use Testcontainers to create a real PostgreSQL database container instead of mockup
// DataJpaTest is used for JPA related test configuration
// AutoConfigureTestDatabase is used to avoid replacing the application's default datasource(container instead of production db)
@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestP3BackendApplication.class})
class BladeProjectRepositoryTest {

    //Inject dependency of tested class
    @Autowired
    BladeProjectRepository bladeProjectRepository;

    @Test
    void findAllBladeProjectsShouldReturnAllBladeProjects() {
        System.out.println("Number of BP" + bladeProjectRepository.findAll());
        // Add tests to interact with bladeProjectRepository
        bladeProjectRepository.save(new BladeProject());
        bladeProjectRepository.save(new BladeProject());
        bladeProjectRepository.save(new BladeProject());



        // Verify that three was added
        assertThat(bladeProjectRepository.findAll()).hasSize(3);


        bladeProjectRepository.deleteAll();

        assertThat(bladeProjectRepository.findAll()).hasSize(0);
    }

    @Test
    void findByIdShouldReturnBladeProject(){
        BladeProject bladeProject = new BladeProject();
        bladeProjectRepository.save(bladeProject);
        assertThat(bladeProjectRepository.findById((long) bladeProject.getId())).isNotNull();
    }
}