package sw_10.p3_backend.Repository;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BladeProjectRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    BladeProjectRepository bladeProjectRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void testRepositoryFunctionality() {
        // Add tests to interact with bladeProjectRepository

        // e.g., bladeProjectRepository.save(new BladeProject(...));
        bladeProjectRepository.save(new BladeProject());
        bladeProjectRepository.save(new BladeProject());
        bladeProjectRepository.save(new BladeProject());

        bladeProjectRepository.findAll().stream().map(BladeProject::getProjectName).forEach(System.out::println);

        assertThat(bladeProjectRepository.findAll()).hasSize(3);
        // and then verify the results
    }

    @Test
    void findByIdShouldReturnBladeProject(){
        BladeProject bladeProject = new BladeProject();
        bladeProjectRepository.save(bladeProject);
        assertThat(bladeProjectRepository.findById((long) bladeProject.getId())).isNotNull();
    }
}