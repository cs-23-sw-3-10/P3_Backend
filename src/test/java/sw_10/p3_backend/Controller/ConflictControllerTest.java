package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Logic.ProjectLogic;

import static org.junit.jupiter.api.Assertions.*;
@Import(ProjectLogic.class)
@GraphQlTest(BladeProjectController.class)
@Testcontainers
class ConflictControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Autowired
    ProjectLogic projectLogic;


}