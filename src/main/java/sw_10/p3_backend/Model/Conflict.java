package sw_10.p3_backend.Model;

import graphql.com.google.common.collect.Sets;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "conflict")
public class Conflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //public enum conflictType { OVERLAP, PERSONNEL, EQUIPMENT }
    private String type;
    private String message;


    @OneToOne
    @JoinColumn(name = "bookingId")
    @Getter(AccessLevel.NONE) Booking booking; //Ensures getter of will not get stuck in endless recursive loop
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop

    @JoinTable(
            name = "conflict_relations",
            joinColumns = @JoinColumn(name = "conflict_id"),
            inverseJoinColumns = @JoinColumn(name = "blade_task_id"))
    @ManyToMany
    Set<BladeTask> relatedBladeTasks;

    //TODO: Need to be updated to fill out all fields in conflict db table and give proper error message with all the related bladetasks
    public Conflict(Booking booking, BladeTask bladeTask, List<BladeTask> relatedBladeTasks) {
        setType(booking.getResourceType()); //What is this used for?
        setMessage("Conflict!: " + booking.getStartDate() + " - " + booking.getEndDate() + " " + booking.getResourceType() + " booking for " + bladeTask.getTaskName() + " is booked with no available resources.");
        setRelatedBladeTasks(Sets.newHashSet(relatedBladeTasks));
    }
}
