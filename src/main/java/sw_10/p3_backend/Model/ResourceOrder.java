package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "resourceOrder")
public class ResourceOrder implements Cloneable {
    private static final int ATTACHMENT_PERIOD = 0;
    private static final int TEST_PERIOD = 1;
    private static final int DETACH_PERIOD = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resourceType;
    private String resourceName;
    private int workHours;
    private int amount;

    @ElementCollection
    private List<Boolean> equipmentAssignmentStatus = new ArrayList<>(Arrays.asList(new Boolean[3]));

    @ManyToOne
    @JoinColumn(name = "bladeTaskId")
    @Getter(AccessLevel.NONE) BladeTask bladeTask; //Ensures getter of will not get stuck in endless recursive loop


    public ResourceOrder(String type, String resourceName, Integer amount, List<Boolean> booleans, Integer integer, BladeTask newBladeTask) {
        this.resourceType = type;
        this.resourceName = resourceName;
        this.amount = amount;
        this.workHours = integer;
        this.bladeTask = newBladeTask;
        // Add false to equipmentAssignmentStatus if it is not already there
        if (booleans.size() < 3) {
            booleans.addAll(Collections.nCopies(3 - booleans.size(), false));
        }
        this.equipmentAssignmentStatus = new ArrayList<>(booleans.subList(0, 3));
    }

    @Override
    public ResourceOrder clone() throws CloneNotSupportedException {
        ResourceOrder cloned = (ResourceOrder) super.clone();
        cloned.id = 0;
        cloned.equipmentAssignmentStatus = new ArrayList<>(equipmentAssignmentStatus);
        for (int i = 0; i < equipmentAssignmentStatus.size(); i++) {
            cloned.equipmentAssignmentStatus.set(i, equipmentAssignmentStatus.get(i));
        }

        return cloned;
    }
}
