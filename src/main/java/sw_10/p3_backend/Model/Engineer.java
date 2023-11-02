package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engineer")
public class Engineer extends Employee{
    @Id
    private int engineerId;
    private String name;

    @OneToMany(mappedBy = "engineer",cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings = new ArrayList<>();

}
