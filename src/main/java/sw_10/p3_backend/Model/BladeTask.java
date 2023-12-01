package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "bladeTask")
@NoArgsConstructor
@Setter @Getter
public class BladeTask implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
  
    private int duration;

    private String testType;
    private int attachPeriod;
    private int detachPeriod;
    private String taskName;
    private Integer testRig;
    private boolean inConflict = false;

    public void addResourceOrder(ResourceOrder resourceOrder) {
        resourceOrders.add(resourceOrder);
        resourceOrder.setBladeTask(this);
    }

    public void addRelatedConflict(Conflict conflict) {
        relatedConflicts.add(conflict);
    }

    public void removeRelatedConflict(Conflict conflict) {
        relatedConflicts.remove(conflict);
    }

    public enum taskState { NOT_STARTED, IN_PROGRESS, COMPLETED }
    private taskState state;

    @ManyToOne
    @JoinColumn(name = "bladeProjectId")
    @Getter(AccessLevel.NONE) BladeProject bladeProject; //Ensures getter of will not get stuck in endless recursive loop


    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<ResourceOrder> resourceOrders = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedBladeTasks")
    Set<Conflict> relatedConflicts = new HashSet<>();

    public BladeTask(LocalDate startDate, int duration, BladeProject bladeProject){
        setStartDate(startDate);
        setDuration(duration);
        setBladeProject(bladeProject);
    }

    public BladeTask(LocalDate startDate, LocalDate endDate, int duration, String testType, int attachPeriod, int detachPeriod, String taskName, int testRig, BladeProject bladeProject) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.testType = testType;
        this.attachPeriod = attachPeriod;
        this.detachPeriod = detachPeriod;
        this.taskName = taskName;
        this.bladeProject = bladeProject;
        this.testRig = testRig;
    }

    public BladeProject getBladeProjectId() {
        return bladeProject;
    }

    @Override
    public BladeTask clone() throws CloneNotSupportedException {
        BladeTask cloned = (BladeTask) super.clone();

        // Reset the ID to indicate a new entity
        cloned.id = 0;

        System.out.println("clone bookings " + cloned.getId());
        // Deep clone bookings
        cloned.bookings = new ArrayList<>();
        for (Booking booking : this.bookings) {
            Booking clonedBooking = booking.clone();
            clonedBooking.setBladeTask(cloned);
            cloned.bookings.add(clonedBooking); // Recursive call
        }

        System.out.println("clone resourceOrders " + cloned.getId());
        // Deep clone resourceOrders
        cloned.resourceOrders = new ArrayList<>();
        for (ResourceOrder resourceOrder : this.resourceOrders) {

            ResourceOrder clonedResourceOrder = resourceOrder.clone();
            clonedResourceOrder.setBladeTask(cloned);
            cloned.resourceOrders.add(clonedResourceOrder); // Recursive call
        }

        return cloned;
    }
}
