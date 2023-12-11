package sw_10.p3_backend.Repository;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.TestP3BackendApplication;
import sw_10.p3_backend.config.SecurityConfig;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@Import(SecurityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestP3BackendApplication.class})
class BladeTaskRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    BladeProjectRepository bladeProjectRepository;
    @Autowired
    BladeTaskRepository bladeTaskRepository;

    private Schedule schedule;
    private BladeProject bladeProject;

    @BeforeEach
    public void setUp() {
        schedule = new Schedule();
        schedule.setActive(false);
        scheduleRepository.save(schedule);

        bladeProject = new BladeProject(schedule, "test", "test", "test", "test");

        bladeProjectRepository.save(bladeProject);

    }

    @Test
    void testFindBladeTasksInRangeShouldReturnBladeTasksInGivenRange() {


        // Given
        BladeTask bt1 = new BladeTask(LocalDate.of(2021,1,2), 10, bladeProject);
        BladeTask bt2 = new BladeTask(LocalDate.of(2021,1,2), 10, bladeProject);
        BladeTask bt3 = new BladeTask(LocalDate.of(2021,3,2), 10, bladeProject);

        bladeTaskRepository.save(bt1);
        bladeTaskRepository.save(bt2);
        bladeTaskRepository.save(bt3);

        // When
        List<BladeTask> bladeTasksInView = bladeTaskRepository.bladeTasksInRange(LocalDate.of(2021,1,1), LocalDate.of(2021,1,9), true);

        List<BladeTask> bladeTasksInEdit = bladeTaskRepository.bladeTasksInRange(LocalDate.of(2021,1,1), LocalDate.of(2021,1,9), false);

        // Then
        assertThat(bladeTasksInEdit).hasSize(2);
        assertThat(bladeTasksInEdit).containsExactlyInAnyOrder(bt1, bt2);

        assertThat(bladeTasksInView).isEmpty();

    }



@Test
void testFindBladeWithNoTestRigShouldReturnListOfBtWithTestRigNull() {

    // Given
    BladeTask bt1 = new BladeTask(LocalDate.of(2021,1,2), 10, bladeProject);
    BladeTask bt2 = new BladeTask(LocalDate.of(2021,1,2), 10, bladeProject);
    BladeTask bt3 = new BladeTask(LocalDate.of(2021,3,2), 10, bladeProject);

    bt1.setTestRig(null);
    bt2.setTestRig(null);
    bt3.setTestRig(1);

    bladeTaskRepository.save(bt1);
    bladeTaskRepository.save(bt2);
    bladeTaskRepository.save(bt3);

    // When
    List<BladeTask> PendingInEdit = bladeTaskRepository.bladeTasksPending(false);

    // Then
    assertThat(PendingInEdit).hasSize(2);
    assertThat(PendingInEdit).doesNotContain(bt3);

}

}