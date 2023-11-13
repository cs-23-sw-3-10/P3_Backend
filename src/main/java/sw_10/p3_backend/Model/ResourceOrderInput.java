package sw_10.p3_backend.Model;

import java.util.List;

public record ResourceOrderInput(String type, Integer amount, Integer workHours, List<Boolean> equipmentAssignmentStatus) {
}
