package sw_10.p3_backend.Logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sw_10.p3_backend.Logic.BladeProjectLogic;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BladeProjectLogicTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private BladeProjectRepository bladeProjectRepository;

    @InjectMocks
    private BladeProjectLogic bladeProjectLogic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        // Arrange
        String name = "Test Project";
        String customer = "Test Customer";
        String projectLeader = "Test Leader";
        Schedule mockSchedule = new Schedule(); // Assuming Schedule is a valid class
        when(scheduleRepository.findScheduleByIsActive(false)).thenReturn(mockSchedule);

        BladeProject expectedProject = new BladeProject(mockSchedule, name, customer, projectLeader);

        // Act
        BladeProject result = bladeProjectLogic.createProject(name, customer, projectLeader);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProject.getProjectName(), result.getProjectName());
        assertEquals(expectedProject.getCustomer(), result.getCustomer());
        assertEquals(expectedProject.getProjectLeader(), result.getProjectLeader());

        verify(scheduleRepository).findScheduleByIsActive(false);
        verify(bladeProjectRepository).save(any(BladeProject.class));
    }

    @Test
    public void testDeleteProject() {
        // Arrange
        Long id = 1L;
        Long id2 = 2L;
        BladeProject mockProject = new BladeProject();
        BladeTask mockTask = new BladeTask();
        List<BladeTask> mockTaskList = List.of(mockTask);
        BladeProject mockProjectWithTasks = new BladeProject();// Assuming BladeTask is a valid class
        mockProjectWithTasks.setBladeTasks(mockTaskList);
        when(bladeProjectRepository.findById(id)).thenReturn(Optional.of(mockProject));
        when(bladeProjectRepository.findById(id2)).thenReturn(Optional.of(mockProjectWithTasks));

        // Act
        String resultNoTasks = bladeProjectLogic.deleteProject(id);
        String resultTasks = bladeProjectLogic.deleteProject(id2);

        // Assert
        assertEquals("Project deleted", resultNoTasks);
        assertEquals("Project has tasks", resultTasks);
    }
}