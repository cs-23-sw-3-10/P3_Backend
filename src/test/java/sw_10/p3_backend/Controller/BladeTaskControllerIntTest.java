package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Logic.TokenLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
@DirtiesContext
@Transactional
public class BladeTaskControllerIntTest {
    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TokenLogic tokenLogic;

    @Autowired
    BladeProjectRepository bladeProjectRepository;

    @Autowired
    BladeTaskRepository bladeTaskRepository;

    Authentication authentication;

    Schedule schedule;
    BladeProject bladeProject;

    @BeforeEach
    public void setUp() {
        schedule = scheduleRepository.save(new Schedule(1,false,null));
        bladeProject = bladeProjectRepository.save(new BladeProject());
        authentication = tokenLogic.authenticate("user", "password");
    }

    @Test
    public void testCreateBladeTaskShouldReturnBladeTask() {
        String token = tokenLogic.generateToken(authentication);

        String query =  String.format("""
                mutation CreateBladeTask {
                         createBladeTask(
                             bladeTask: {
                                 startDate: "2021-05-05"
                                 endDate: null
                                 duration: 5
                                 attachPeriod: 0
                                 detachPeriod: 0
                                 testRig: 0
                                 bladeProjectId: 1
                                 taskName: "henning"
                                 testType: "test"
                                 resourceOrders: []
                             }
                         ) {
                             startDate
                             endDate
                             duration
                             inConflict
                         }
                     }
                """);
        BladeTask bladeTask = httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("createBladeTask").entity(BladeTask.class).get();

        assertThat(bladeTask.getStartDate()).isNull();
        assertThat(bladeTask.getEndDate()).isNull();
        assertThat(bladeTask.getDuration()).isEqualTo(5);
        assertThat(bladeTask.isInConflict()).isFalse();
    }

    @Test
    public void testCreateBladeTaskWithUnavailableResourceShouldReturnBladeTaskinConflict(){
        String token = tokenLogic.generateToken(authentication);

        String query =  String.format("""
                mutation CreateBladeTask {
                         createBladeTask(
                             bladeTask: {
                                 startDate: "2021-05-05"
                                 endDate: null
                                 duration: 5
                                 attachPeriod: 0
                                 detachPeriod: 0
                                 testRig: 1
                                 bladeProjectId: 1
                                 taskName: "henning"
                                 testType: "test"
                                 resourceOrders: [
                                     {
                                         resourceType: "equipment"
                                         resourceName: "testDoesNotExist"
                                         workHours: 0
                                         equipmentAssignmentStatus: [true, true]
                                     }
                                 ]
                             }
                         ) {
                             startDate
                             endDate
                             duration
                             inConflict
                         }
                     }
                """);

        BladeTask bladeTask = httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("createBladeTask").entity(BladeTask.class).get();

        assertThat(bladeTask.getStartDate()).isEqualTo("2021-05-05");
        assertThat(bladeTask.getEndDate()).isEqualTo("2021-05-09");
        assertThat(bladeTask.getDuration()).isEqualTo(5);
        assertThat(bladeTask.isInConflict()).isTrue();
    }

}
