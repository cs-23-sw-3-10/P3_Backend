package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.TestP3BackendApplication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
@ContextConfiguration(classes = {TestP3BackendApplication.class})
class EngineerControllerIntTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Test
    void findAllEngineersShouldReturnAllEngineers(){
        String document = """
                query AllEngineers {
                    AllEngineers {
                        id
                        name
                        workHours
                        maxWorkHours
                    }
                }
                """;


        List<Engineer> engineers = this.httpGraphQlTester.document(document).
                execute().
                path("AllEngineers").
                entityList(Engineer.class).
                get();

        assertThat(engineers).hasSize(0);
    }




}