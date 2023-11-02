package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private int startDate;
    private int endDate;

    @ManyToOne
    @JoinColumn(name = "bladeTaskid")
    @Getter(AccessLevel.NONE) private BladeTask bladeTask;
    @ManyToOne
    @JoinColumn(name = "engineerid")
    @Getter(AccessLevel.NONE) private Engineer engineer;
    @ManyToOne
    @JoinColumn(name = "technicianid")
    @Getter(AccessLevel.NONE) private Technician technician;
    @ManyToOne
    @JoinColumn(name = "equipmentid")
    @Getter(AccessLevel.NONE) private Equipment equipment;
}
