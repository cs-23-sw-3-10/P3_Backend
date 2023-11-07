package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;

import static org.junit.jupiter.api.Assertions.*;

@Import(EngineerRepository.class)
@GraphQlTest(EngineerController.class)
class EngineerControllerIntTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void findAllEngineersShouldReturnAllEngineers(){
        String document = """
                query {
                  allEngineers {
                    id
                    name
                    email
                    phone
                  }
                }
                """;


        graphQlTester.document(document).execute().path("allEngineers").entityList(Engineer.class).hasSize(4);
    }




}