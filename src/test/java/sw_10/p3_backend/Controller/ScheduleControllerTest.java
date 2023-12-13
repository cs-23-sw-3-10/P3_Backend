package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.*;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
@DirtiesContext
class ScheduleControllerTest {
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

    @BeforeEach
    public void setUp() {
        scheduleRepository.save(new Schedule(1,true,null));
        scheduleRepository.save(new Schedule(2,false,null));
        authentication = tokenLogic.authenticate("user", "password");

    }

    @Order(1)
    @Test
    void testFindAllSchedulesShouldReturnAllSchedules() {
        String document = """
                query AllSchedules {
                    AllSchedules {
                      id
                    }
                }
                """;
        List<Schedule> schedules = this.httpGraphQlTester.document(document).
                execute().
                path("AllSchedules").
                entityList(Schedule.class).
                get();

        assertEquals(2, schedules.size());
    }

    @Order(2)
    @Test
    void testCloneAndReplaceScheduleShouldReturnUpdatedScheduleAsACloneOfEdit() {

        BladeProject newBladeProject1 = new BladeProject();
        BladeProject newBladeProject2 = new BladeProject();

        Schedule activeSchedule = scheduleRepository.findById(2L).get();

        newBladeProject1.setSchedule(activeSchedule);
        newBladeProject2.setSchedule(activeSchedule);

        bladeProjectRepository.save(newBladeProject1);
        bladeProjectRepository.save(newBladeProject2);

        BladeTask newBladeTask1 = new BladeTask();
        BladeTask newBladeTask2 = new BladeTask();
        BladeTask newBladeTask3 = new BladeTask();

        newBladeTask1.setBladeProject(newBladeProject1);
        newBladeTask2.setBladeProject(newBladeProject1);
        newBladeTask3.setBladeProject(newBladeProject1);

        bladeTaskRepository.save(newBladeTask1);
        bladeTaskRepository.save(newBladeTask2);
        bladeTaskRepository.save(newBladeTask3);

        String token = tokenLogic.generateToken(authentication);


        String document = """
                mutation cloneScheduleAndReplace {
                    cloneScheduleAndReplace {
                      id
                      bladeProject {
                        id
                        bladeTasks {
                          id
                        }
                      }
                    }
                }
                """;
        Schedule newViewSchedule = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(document).
                execute().
                path("cloneScheduleAndReplace").
                entity(Schedule.class).
                get();


        assertThat(newViewSchedule).isNotSameAs(activeSchedule);
        assertThat(newViewSchedule).isNotNull();
        assertThat(newViewSchedule.getId()).isEqualTo(1);
        assertThat(newViewSchedule.getBladeProject().size()).isEqualTo(2);
        assertThat(newViewSchedule.getBladeProject().get(0).getBladeTasks().size()).isEqualTo(3);

    }

    @Order(3)
    @Test
    void testDiscardEditChangesShouldReturnCopyOfViewMode() {
        String token = tokenLogic.generateToken(authentication);

        Schedule editSchedule = scheduleRepository.findById(2L).get();

        BladeProject bladeProjectTest = bladeProjectRepository.findByIdWithBladeTasks(1L);

        assertThat(bladeProjectTest).isNotNull();
        assertThat(bladeProjectTest.getBladeTasks().size()).isEqualTo(3);

        bladeProjectRepository.deleteAll(bladeProjectRepository.findAllBySchedule(false));

        BladeProject bladeProjectTestAfterDelete = bladeProjectRepository.findByIdWithBladeTasks(1L);

        assertThat(bladeProjectTest).isNotNull();
        assertThat(bladeProjectTest.getBladeTasks().size()).isEqualTo(3);


        String document = """
                mutation discardEditChanges {
                    discardEditChanges {
                      id
                      bladeProject {
                        id
                        bladeTasks {
                          id
                        }
                      }
                    }
                }
                """;

        Schedule editAfterDiscard = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(document).
                execute().
                path("discardEditChanges").
                entity(Schedule.class).
                get();

        assertThat(editAfterDiscard).isNotSameAs(editSchedule);
        assertThat(editAfterDiscard).isNotNull();
        assertThat(editAfterDiscard.getId()).isEqualTo(2);
        assertThat(editAfterDiscard.getBladeProject().size()).isEqualTo(2);
        assertThat(editAfterDiscard.getBladeProject().get(0).getBladeTasks().size()).isEqualTo(3);

        scheduleRepository.deleteAll();
    }

}