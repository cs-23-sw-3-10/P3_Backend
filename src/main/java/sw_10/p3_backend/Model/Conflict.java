package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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
    private String message;


    @OneToOne
    @JoinColumn(name = "bookingId")

    @Getter(AccessLevel.NONE) Booking booking; //Ensures getter of will not get stuck in endless recursive loop
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop

    public Conflict(Booking booking, BladeTask bladeTask) {
        setType(booking.getResourceType());
        setMessage("Conflict!: " + booking.getStartDate() + " - " + booking.getEndDate() + " " + booking.getResourceType() + " booking for " + bladeTask.getTaskName() + " is booked with no available resources.");
    }

    @Override
    public Conflict clone() throws CloneNotSupportedException {
        Conflict cloned = (Conflict) super.clone();

        // Reset the ID to indicate a new entity
        cloned.id = 0;
        return cloned;
    }

}
