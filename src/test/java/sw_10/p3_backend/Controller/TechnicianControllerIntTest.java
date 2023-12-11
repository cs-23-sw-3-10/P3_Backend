package sw_10.p3_backend.Controller;


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
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
@Testcontainers
@Transactional
@DirtiesContext
class TechnicianControllerIntTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    TokenLogic tokenLogic;
    Authentication authentication;


    @Test
    void testAllTechnicians() {
        Technician technician1 = new Technician();
        Technician technician2 = new Technician();
        Technician technician3 = new Technician();

        technicianRepository.save(technician1);
        technicianRepository.save(technician2);
        technicianRepository.save(technician3);



        String query = String.format("""
                query {
                  AllTechnicians {
                    type
                    maxWorkHours
                    count
                  }
                }
                """);


        List<Technician> technicianList = this.httpGraphQlTester.document(query).execute().errors().verify().path("AllTechnicians").entityList(Technician.class).get();

        assertThat(technicianList).isNotNull();
        assertThat(technicianList.size()).isEqualTo(3);

    }

    @Test
    void testTechnicianByType() {
        Technician technician1 = new Technician();
        Technician technician2 = new Technician();
        Technician technician3 = new Technician();

        technician1.setType("type1");
        technician1.setMaxWorkHours(1);
        technician1.setCount(1);
        technician2.setType("type2");
        technician3.setType("type3");

        technicianRepository.save(technician1);
        technicianRepository.save(technician2);
        technicianRepository.save(technician3);

        String query = String.format("""
                query {
                  TechnicianByType(type: "type1") {
                    type
                    maxWorkHours
                    count
                  }
                }
                """);

        Technician technician = this.httpGraphQlTester.document(query).execute().errors().verify().path("TechnicianByType").entity(Technician.class).get();

        assertThat(technician).isNotNull();
        assertThat(technician.getType()).isEqualTo("type1");
        assertThat(technician.getMaxWorkHours()).isEqualTo(1);
        assertThat(technician.getCount()).isEqualTo(1);
    }

    @Test
    void testCreateTechnician() {
        authentication = tokenLogic.authenticate("user", "password");
        String token = tokenLogic.generateToken(authentication);

        String query = String.format("""
                mutation {
                  CreateTechnician(type: "type1", maxWorkHours: 1, count: 1) {
                    type
                    maxWorkHours
                    count
                  }
                }
                """);

        Technician technician = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("CreateTechnician").entity(Technician.class).get();

        assertThat(technician).isNotNull();
        assertThat(technician.getType()).isEqualTo("type1");
        assertThat(technician.getMaxWorkHours()).isEqualTo(1);
        assertThat(technician.getCount()).isEqualTo(1);
    }

    @Test
    void testCreateTechnicianIfTypeExists() {
        authentication = tokenLogic.authenticate("user", "password");
        String token = tokenLogic.generateToken(authentication);

        Technician technician1 = new Technician();
        technician1.setType("type1");

        String query = String.format("""
                mutation {
                  CreateTechnician(type: "type1", maxWorkHours: 2, count: 2) {
                    type
                    maxWorkHours
                    count
                  }
                }
                """);

        Technician technician = this.httpGraphQlTester.mutate().header("Authorization", "Bearer " + token).build().document(query).execute().errors().verify().path("CreateTechnician").entity(Technician.class).get();

        assertThat(technician).isNotNull();
        assertThat(technician.getType()).isEqualTo("type1");
        assertThat(technician.getMaxWorkHours()).isEqualTo(2);
        assertThat(technician.getCount()).isEqualTo(2);
    }
}