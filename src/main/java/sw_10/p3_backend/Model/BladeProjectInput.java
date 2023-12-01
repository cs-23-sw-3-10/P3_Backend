package sw_10.p3_backend.Model;

public record BladeProjectInput (Long scheduleId, String startDate, String endDate, String customer,
                                 String projectLeader, String projectName){}
