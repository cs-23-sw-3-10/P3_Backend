package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "technician")
public class Technician extends Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private int workHours;
    private int maxWorkHours;
    private int count;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
