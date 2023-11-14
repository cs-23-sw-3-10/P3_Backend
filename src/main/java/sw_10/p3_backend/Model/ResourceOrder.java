package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "resourceOrder")
public class ResourceOrder {
    private static final int ATTACHMENT_PERIOD = 0;
    private static final int TEST_PERIOD = 1;
    private static final int DETACH_PERIOD = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private int workHours;
    private int amount;
    private boolean[] equipmentAssignmentStatus = new boolean[3];

    @ManyToOne
    @JoinColumn(name = "bladeTaskId")
    @Getter(AccessLevel.NONE) BladeTask bladeTask; //Ensures getter of will not get stuck in endless recursive loop


    public ResourceOrder(LocalDate startDate, LocalDate endDate, String type, Integer amount, List<Boolean> booleans, Integer integer, BladeTask newBladeTask) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.amount = amount;
        this.workHours = integer;
        this.bladeTask = newBladeTask;
        for (int i = 0; i < booleans.size() && i < this.equipmentAssignmentStatus.length; i++) {
            this.equipmentAssignmentStatus[i] = booleans.get(i);
        }
    }
}
