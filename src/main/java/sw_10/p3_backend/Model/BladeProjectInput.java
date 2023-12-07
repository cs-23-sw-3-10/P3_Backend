package sw_10.p3_backend.Model;

import java.util.List;

public record BladeProjectInput (Long scheduleId, String startDate, String endDate, String customer,
                                 String projectLeader, String projectName, List<ResourceOrderInput> resourceOrders){}
