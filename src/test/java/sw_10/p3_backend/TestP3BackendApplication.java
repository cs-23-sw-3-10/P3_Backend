package sw_10.p3_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;


@TestConfiguration(proxyBeanMethods = false)
public class TestP3BackendApplication {


    @Bean
    @ServiceConnection
    PostgreSQLContainer postgreSQLContainer(){
        return new PostgreSQLContainer<>("postgres:16.0");
    }
    public static void main(String[] args) {
        SpringApplication.from(P3BackendApplication::main).with(TestP3BackendApplication.class).run(args);
    }

}
