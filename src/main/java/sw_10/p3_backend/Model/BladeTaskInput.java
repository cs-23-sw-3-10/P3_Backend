package sw_10.p3_backend.Model;

import java.time.LocalDate;

public record BladeTaskInput(LocalDate startDate, LocalDate endDate, Integer duration, Integer attachPeriod, Integer detachPeriod,
                             Integer testRig, Integer bladeProjectId, String taskName, String testType) {
}
