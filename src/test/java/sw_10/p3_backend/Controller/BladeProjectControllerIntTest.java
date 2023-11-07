package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import sw_10.p3_backend.Logic.BladeTaskLogic;
import sw_10.p3_backend.Logic.ProjectLogic;
import sw_10.p3_backend.Model.BladeProject;

@Import({ProjectLogic.class})
@GraphQlTest(BladeProjectController.class)
class BladeProjectControllerIntTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void testFindAllBladeProjectShouldReturnAllBladeProjects(){
        //language = GraphQL
        String document = """
                query {
                  allBladeProjects {
                    id
                    name
                    customer
                    projectLeader
                  }
                }
                """;
        graphQlTester.document(document).execute().path("allBladeProjects").entityList(BladeProject.class).hasSize(4);

    }

}