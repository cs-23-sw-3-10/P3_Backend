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
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;
}
