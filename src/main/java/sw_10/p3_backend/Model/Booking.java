package sw_10.p3_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private String bookingDate;

    @ManyToOne
    @JoinColumn(name = "bladeTaskid")
    private BladeTask bladeTask;
    @ManyToOne
    @JoinColumn(name = "engineerid")
    private Engineer engineer;
    @ManyToOne
    @JoinColumn(name = "technicianid")
    private Technician technician;
    @ManyToOne
    @JoinColumn(name = "equipmentid")
    private Equipment equipment;
}
