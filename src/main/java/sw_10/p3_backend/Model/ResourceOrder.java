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

    @ElementCollection
    private List<Boolean> equipmentAssignmentStatus = new ArrayList<>(Arrays.asList(new Boolean[2]));

    @ManyToOne
    @JoinColumn(name = "bladeTaskId")
    BladeTask bladeTask; //Ensures getter of will not get stuck in endless recursive loop

    @ManyToOne
    @JoinColumn(name = "bladeProjectId")
    @Getter(AccessLevel.NONE) BladeProject bladeProject; //Ensures getter of will not get stuck in endless recursive loop


    /**
     * This constructor is used for creating a resource order for a blade task
     * @param type Type of resource
     * @param resourceName Name of resource
     * @param booleans List of booleans indicating if the resource is assigned to the attachment or detach period
     * @param integer Number of work hours
     * @param bladeTask Blade task the resource order is for
     */
    public ResourceOrder(String type, String resourceName, List<Boolean> booleans, Integer integer, BladeTask bladeTask) {
        this.resourceType = type.toLowerCase();
        this.resourceName = resourceName.toLowerCase();
        this.workHours = integer;
        this.bladeTask = bladeTask;
        //Fills up remaining list with false, if booleans.size() < 2(The expected size)
        if (booleans.size() < 2) {
            booleans.addAll(Collections.nCopies(2 - booleans.size(), false));
        }
        this.equipmentAssignmentStatus = new ArrayList<>(booleans.subList(0, 2));
    }

    /**
     * This constructor is used for creating a resource order for a blade project
     * @param type Type of resource
     * @param resourceName Name of resource
     * @param booleans //Not used
     * @param workHours Number of work hours
     * @param BladeProject Blade project the resource order is for
     */
    public ResourceOrder(String type, String resourceName, List<Boolean> booleans, Integer workHours, BladeProject BladeProject) {
        this.resourceType = type.toLowerCase();
        this.resourceName = resourceName.toLowerCase();
        this.workHours = workHours;
        this.bladeProject = BladeProject;

        //Fills up remaining list with false, if booleans.size() < 2(The expected size)
        if (booleans.size() < 2) {
            booleans.addAll(Collections.nCopies(2 - booleans.size(), false));
        }
        this.equipmentAssignmentStatus = new ArrayList<>(booleans.subList(0, 2));
    }

    /**
     * This method is used to clone a resource order
     * @return Cloned resource order
     * @throws CloneNotSupportedException
     */
    @Override
    public ResourceOrder clone() throws CloneNotSupportedException {
        ResourceOrder cloned = (ResourceOrder) super.clone();

        // Reset the ID to indicate a new entity
        cloned.id = 0;
        cloned.equipmentAssignmentStatus = new ArrayList<>(equipmentAssignmentStatus);
        for (int i = 0; i < equipmentAssignmentStatus.size(); i++) {
            cloned.equipmentAssignmentStatus.set(i, equipmentAssignmentStatus.get(i));
        }

        return cloned;
    }

    public BladeProject fetchBladeProject() {
        return this.bladeProject;
    }
}
