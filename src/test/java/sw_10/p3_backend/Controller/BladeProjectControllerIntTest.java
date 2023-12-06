package sw_10.p3_backend.Controller;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Logic.TokenLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
class BladeProjectControllerIntTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TokenLogic tokenLogic;

    @Autowired
    BladeProjectRepository bladeProjectRepository;


    @Test
    void testFindAllBladeProjectShouldReturnAllBladeProjects(){



        List<BladeProject> bladeProject = this.httpGraphQlTester.document("""
    query AllBladeProjects {
            AllBladeProjects {
        id
        startDate
        endDate
        customer
        projectLeader
        projectName
    }
}
                """).execute().errors().verify().path("AllBladeProjects").entityList(BladeProject.class).get();


        //language = GraphQL
    assertThat(bladeProject).hasSize(0);
    }

    @Test
    void createBladeTask(){

        scheduleRepository.save(new Schedule(1,true,null));

        Authentication authentication = tokenLogic.authenticate("user", "password");

        //Generate a token from the Authentication object
        String token = tokenLogic.generateToken(authentication);

        BladeProject bladeProject = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().
                document("""
                mutation CreateBladeProject {
                    createBladeProject(
                        name: "TestBP1"
                        customer: "Bestas"
                        projectLeader: "Henning"
                    ) {
                        id
                        startDate
                        endDate
                        customer
                        projectLeader
                        projectName
                    }
                }
                """).execute().errors().verify().path("createBladeProject").entity(BladeProject.class).get();
        assertThat(bladeProject.getProjectName()).isEqualTo("TestBP1");
        assertThat(bladeProject.getCustomer()).isEqualTo("Bestas");
        assertThat(bladeProject.getProjectLeader()).isEqualTo("Henning");

        bladeProjectRepository.deleteAll();





    }

}