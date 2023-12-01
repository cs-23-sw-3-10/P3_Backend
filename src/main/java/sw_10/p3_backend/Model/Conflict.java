package sw_10.p3_backend.Model;

import graphql.com.google.common.collect.Sets;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "conflict")
public class Conflict implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //public enum conflictType { OVERLAP, PERSONNEL, EQUIPMENT }
    private String type;

    @Column(length = 2000)
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

    public Conflict(Booking booking, BladeTask bladeTask, Set<BladeTask> hashedBladeTasks) {
        setType(booking.getResourceType());
        setBooking(booking);

        String errorMessage = "Conflict! \n" +
                                "Booking of equipment: " + booking.getResourceName() + " in period " + booking.getStartDate() + " - " + booking.getEndDate() + " for " + bladeTask.getTaskName() + " was not possible due to lack of resources.\n" +
                                "Bladetasks: ";
        boolean first = true;
        for (BladeTask hashedBladeTask : hashedBladeTasks) {
            if(!first){
                errorMessage += ", ";
            }
            errorMessage += hashedBladeTask.getTaskName();
            first = false;
        }
        errorMessage += " has bookings of this equipment in this period.";

        System.out.println(errorMessage);
        setMessage(errorMessage);
        setRelatedBladeTasks(hashedBladeTasks);
    }

    @Override
    public Conflict clone() throws CloneNotSupportedException {
        Conflict cloned = (Conflict) super.clone();

        // Reset the ID to indicate a new entity
        cloned.id = 0;
        return cloned;
    }

    public Booking fetchBooking(){
        return booking;
    }

}
