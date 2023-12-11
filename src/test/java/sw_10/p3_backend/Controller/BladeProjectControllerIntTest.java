package sw_10.p3_backend.Controller;


import jakarta.transaction.Transactional;
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
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
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

    @Autowired
    BladeTaskRepository bladeTaskRepository;

    Authentication authentication;

    @BeforeEach
    public void setUp() {
        scheduleRepository.save(new Schedule(1,false,null));
        authentication = tokenLogic.authenticate("user", "password");

    }

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
    void testCreateBladeProjectShouldReturnCreatedBladeProject(){

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

    @Test
    public void testFindBladeProjectByIdShouldReturnCorrectBladeProject(){
        //Arrange
        BladeProject bladeProject = new BladeProject(scheduleRepository.findScheduleByIsActive(false), "Test", "Test", "Test", "Test");
        BladeProject btsaved = bladeProjectRepository.save(bladeProject);
        int id = btsaved.getId();

        String query = String.format("""
        query BladeProjectById {
            BladeProjectById(id: %d) {
                id
                startDate
                endDate
                customer
                projectLeader
                projectName
            }
        }
        """, id);

        //Act
        BladeProject bladeProject1 = this.httpGraphQlTester.document(query).execute().errors().verify().path("BladeProjectById").entity(BladeProject.class).get();

        //Assert
        assertThat(bladeProject1.getId()).isEqualTo(id);
        assertThat(bladeProject1.getCustomer()).isEqualTo("Test");
        assertThat(bladeProject1.getProjectLeader()).isEqualTo("Test");
        assertThat(bladeProject1.getProjectName()).isEqualTo("Test");

        bladeProjectRepository.deleteAll();
    }

    @Test
    public void testUpdateBladeProjectShouldReturnBladeProjectWithChangesApplied(){
        BladeProject bladeProjectPreUpdate = new BladeProject(scheduleRepository.findScheduleByIsActive(false), "Test", "Test", "Test", "Test");
        BladeProject btsaved = bladeProjectRepository.save(bladeProjectPreUpdate);
        int id = btsaved.getId();

        String query = String.format("""
                mutation UpdateBladeProject {
                    updateBladeProject(
                        bpId: %d
                        updates: {
                            customer: "Test2"
                            projectLeader: "Test2"
                            projectName: "Test2"
                        }
                    ) {
                        id
                        startDate
                        endDate
                        customer
                        projectLeader
                        projectName
                    }
                }
                """,id);

        BladeProject updatedBladeProject = this.httpGraphQlTester.mutate().build().document(query).execute().errors().verify().path("updateBladeProject").entity(BladeProject.class).get();

        assertThat(updatedBladeProject.getId()).isEqualTo(id);
        assertThat(updatedBladeProject.getCustomer()).isEqualTo("Test2");
        assertThat(updatedBladeProject.getProjectLeader()).isEqualTo("Test2");
        assertThat(updatedBladeProject.getProjectName()).isEqualTo("Test2");

        bladeProjectRepository.deleteAll();
    }

    @Test
    public void testDeleteBladeProjectShouldReturnEmptyBladeProjectList(){
        //Arrange
        BladeProject bladeProject = new BladeProject(scheduleRepository.findScheduleByIsActive(false), "Test", "Test", "Test", "Test");
        BladeProject savedbp = bladeProjectRepository.save(bladeProject);
        int id = savedbp.getId();

        String query = String.format("""
                mutation DeleteBladeProject {
                    deleteBladeProject(id: %d)
                }
                """, id);


        //Generate a token from the Authentication object
        String token = tokenLogic.generateToken(authentication);

        //Act
        assertThat(bladeProjectRepository.findAll()).hasSize(1); //Assert that the list is not empty

        String deleteBladeProject = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("deleteBladeProject").entity(String.class).get();

        //Assert
        assertThat(deleteBladeProject).isEqualTo("BladeProject with id: %d has been deleted", id);
        assertThat(bladeProjectRepository.findAll()).isEmpty(); //Assert that the list is empty

        bladeProjectRepository.deleteAll();
    }

    @Test
    public void testDeleteBladeProjectWithAssociatedBladeTasksShouldReturnErrorMessage(){
        //Arrange
        BladeProject bladeProject = new BladeProject(scheduleRepository.findScheduleByIsActive(false), "TestCantDeletePN", "TestCantDeleteC", "TestCantDeletePL", "Test");
        BladeProject savedbp = bladeProjectRepository.save(bladeProject);
        int id = savedbp.getId();

        BladeTask bladeTask = new BladeTask(null, 0, savedbp);
        bladeTaskRepository.save(bladeTask);


        //Generate a token from the Authentication object
        String token = tokenLogic.generateToken(authentication);

        //Act
        assertThat(bladeProjectRepository.findAll()).hasSize(1); //Assert that the list is not empty

        String query = String.format("""
                mutation DeleteBladeProject {
                    deleteBladeProject(id: %d)
                }
                """, id);

        String deleteBladeProject = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query
             ).execute().errors().verify().path("deleteBladeProject").entity(String.class).get();

        //Assert
        assertThat(deleteBladeProject).isEqualTo("BladeProject with id: %d has bladetasks and can therefore not be deleted", id);

        BladeProject bladeProjectEnsureNotDeleted = bladeProjectRepository.findById((long) id).get();
        assertThat(bladeProjectEnsureNotDeleted.getProjectName()).isEqualTo("TestCantDeletePN");//Assert that the list is empty
        assertThat(bladeProjectEnsureNotDeleted.getProjectLeader()).isEqualTo("TestCantDeletePL");//Assert that the list is empty
        assertThat(bladeProjectEnsureNotDeleted.getCustomer()).isEqualTo("TestCantDeleteC");//Assert that the list is empty
        assertThat(bladeProjectRepository.findAll()).hasSize(1); //Assert that the list is not empty

        bladeTaskRepository.deleteAll();
        bladeProjectRepository.deleteAll();

    }

}