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
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.exception.InputInvalidException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BladeTaskLogicTest {

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
    }

    @Test
    void TestDateBeforeTodayWhenCreatingBladeTask() {
        //Arrange
        BladeTaskInput input = new BladeTaskInput(1L,LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), 1, 1, 1, 1, 1, "TestName", "TestType", null);
        //Act

        //Assert
        assertThrows(InputInvalidException.class, () -> bladeTaskLogic.createBladeTask(input));

    }

    @Test
    void TestCreationOfBladeTaskShouldReturnBladeTask() {
        //Arrange
        BladeProject mockProject = new BladeProject();
        when(bladeProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        BladeTaskInput input = new BladeTaskInput(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2022, 1, 2), 1, 1, 1, 1, 1, "TestName", "TestType", null);
        //Act
        BladeTask result = bladeTaskLogic.createBladeTask(input);
        //Assert
        assertNotNull(result);
        assertEquals(LocalDate.of(2024, 1, 1), result.getStartDate());
    }
}