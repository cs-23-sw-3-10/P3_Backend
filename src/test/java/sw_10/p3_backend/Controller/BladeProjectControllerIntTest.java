package sw_10.p3_backend.Controller;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@SpringBootTest
@AutoConfigureHttpGraphQlTester
@Testcontainers
class BladeProjectControllerIntTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");


    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Test
    void testFindAllBladeProjectShouldReturnAllBladeProjects(){
        //language = GraphQL

    }

}