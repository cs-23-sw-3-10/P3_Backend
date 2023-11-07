package sw_10.p3_backend.Repository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;

// ActiveProfiles specify that application-test.properties will be used for database config(In order to not inject data from data.sql)
// Use Testcontainers to create a real PostgreSQL database container instead of mockup
// DataJpaTest is used for JPA related test configuration
// AutoConfigureTestDatabase is used to avoid replacing the application's default datasource(container instead of production db)
@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BladeProjectRepositoryTest {

    // This container starts a PostgreSQL database container

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    //Inject dependency of tested class
    @Autowired
    BladeProjectRepository bladeProjectRepository;


    //Test if connection to container was established
    @Test
    void connectionEstablished() {
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void findAllBladeProjectsShouldReturnAllBladeProjects() {
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