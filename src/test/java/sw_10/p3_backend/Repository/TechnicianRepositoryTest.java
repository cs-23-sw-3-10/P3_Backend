package sw_10.p3_backend.Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Technician;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TechnicianRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TechnicianRepository technicianRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void testFindByIdShouldReturnTechnician(){
        technicianRepository.save(new Technician(1,"smith", 20, 37, 3, null));

        assertThat(technicianRepository.findByType("smith").getType()).isEqualTo("smith");
    }
}
