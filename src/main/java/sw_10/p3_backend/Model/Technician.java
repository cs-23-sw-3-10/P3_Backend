package sw_10.p3_backend.Model;

import jakarta.persistence.*;

import java.util.ArrayList;

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


    @OneToMany(mappedBy = "technician",cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings = new ArrayList<>();
}
