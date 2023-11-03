package sw_10.p3_backend.Model;

import jakarta.persistence.*;

import java.awt.print.Book;
import java.util.ArrayList;

@Entity
@Table(name = "engineer")
public class Engineer extends Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int workHours;
    private int maxWorkHours;

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings = new ArrayList<>();

}
