package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technician")
@Getter @Setter
public class Technician extends Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int technicianId;
    private String type;

    @OneToMany(mappedBy = "technician",cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
