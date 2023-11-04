package sw_10.p3_backend.Model;

public record BladeTaskInput(Integer startDate, Integer endDate, Integer duration, Integer attachPeriod, Integer detachPeriod,
                             Integer testRig, Integer bladeProjectId, String taskName, String testType) {
}
