package sw_10.p3_backend.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.exception.InputInvalidException;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BladeTaskLogicTest {
    BladeTaskInput input;
    BladeTask result;

    @Mock
    private BladeProjectLogic bladeProjectLogic;
    @Mock
    private BladeProjectRepository bladeProjectRepository;
    @Mock
    private BookingLogic bookingLogic;

    @Mock
    private BladeTaskRepository bladeTaskRepository;


    @InjectMocks
    private BladeTaskLogic bladeTaskLogic;



    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        BladeProject mockProject = new BladeProject();
        when(bladeProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));

        input = new BladeTaskInput(
                1L,
                LocalDate.of(2023,11,28),
                15,
                2,
                3,
                1,
                1,
                "TaskName",
                "TaskType",
                null
        );

        result = bladeTaskLogic.createBladeTask(input);
    }

    @Test
    void TestDateBeforeTodayWhenCreatingBladeTask() {
        //Arrange
        input = new BladeTaskInput(1L,LocalDate.of(2021, 1, 1), 1, 1, 1, 1, 1, "TestName", "TestType", null);

        //Assert
        assertThrows(InputInvalidException.class, () -> bladeTaskLogic.createBladeTask(input));
    }

    @Test
    public void testBladeTasksInRangeSubInitialLoad() {
        // Arrange
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";
        boolean isActive = true;
        BladeTask mockTask = new BladeTask();
        BladeTask mockTask2 = new BladeTask();
        List<BladeTask> initialTasks = List.of(mockTask);
        List<BladeTask> updatedTasks1 = List.of(mockTask, mockTask2);




        when(bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate) ,isActive)).thenReturn(initialTasks);

        // Act
        StepVerifier.create(bladeTaskLogic.bladeTasksInRangeSub(startDate, endDate ,isActive).take(1))
                .expectNext(initialTasks)
                .verifyComplete();

        // Assertions for initial load
    }
    @Test
    void testBladeTasksInRangeSub() {
        // Arrange
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";
        boolean isActive = true;

        BladeTask mockTask = new BladeTask();
        BladeTask mockTask2 = new BladeTask();
        BladeTask mockTask3 = new BladeTask();


        List<BladeTask> initialTasks = List.of(mockTask);
        List<BladeTask> updatedTasks1 = List.of(mockTask, mockTask2);
        List<BladeTask> updatedTasks2 = List.of(mockTask, mockTask2, mockTask3);


        when(bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive)).thenReturn(initialTasks, updatedTasks1, updatedTasks2);

        // Act
        StepVerifier.create(bladeTaskLogic.bladeTasksInRangeSub(startDate, endDate ,isActive).take(3))// Take 3 updates from database (initial load + 2 updates)
                .expectNext(initialTasks)// Initial load of data
                .then((() ->  bladeTaskLogic.onDatabaseUpdate()))// Trigger update of data in database
                .expectNext(updatedTasks1)// Expect updated data from database after update
                .then((() ->  bladeTaskLogic.onDatabaseUpdate()))
                .expectNext(updatedTasks2)
                .verifyComplete();


    }

    void TestCreationOfBladeTaskShouldReturnBladeTask() {

        //Arrange
        BladeProject mockProject = new BladeProject();
        when(bladeProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));

        //BladeTaskInput input = new BladeTaskInput(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2022, 1, 2), 1, 1, 1, 1, 1, "TestName", "TestType", null);
        //Act

        
        BladeTask result = bladeTaskLogic.createBladeTask(input);

        //Assert
        assertNotNull(result);
        assertEquals(result.getClass(), BladeTask.class);
    }

    @Test
    void TestStartDateIsUnaffectedByAttachPeriod(){
        //Assert
        assertEquals(LocalDate.of(2023,11,28), result.getStartDate());
    }

    @Test
    void TestDurationIsSumOfAttachAndDetachPeriod(){
        int totalDuration = input.duration() + input.attachPeriod() + input.detachPeriod();
        assertEquals(result.getDuration(), totalDuration);
    }

    @Test
    void TestEndDateIsSetAccordingToDurationAttachAndDetachPeriod(){
        int totalDuration = input.duration() + input.attachPeriod() + input.detachPeriod();
        assertEquals(result.getEndDate(),input.startDate().plusDays(totalDuration));
    }


}

