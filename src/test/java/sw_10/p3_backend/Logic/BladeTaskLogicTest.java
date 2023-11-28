package sw_10.p3_backend.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.Model.ResourceOrderInput;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.exception.InputInvalidException;

import java.time.LocalDate;
import java.util.ArrayList;
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
    void TestCreationOfBladeTaskShouldReturnBladeTask() {
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