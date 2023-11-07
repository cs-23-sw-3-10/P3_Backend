package sw_10.p3_backend.Model;

import java.util.Date;

public record BladeTaskInput(Date startDate, Date endDate, Integer duration, Integer attachPeriod, Integer detachPeriod,
                             Integer testRig, Integer bladeProjectId, String taskName, String testType) {
}
