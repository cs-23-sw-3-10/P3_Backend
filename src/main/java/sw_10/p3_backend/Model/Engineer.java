package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engineer")
@Getter @Setter
public class Engineer extends Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int engineerId;
    private String name;

    @OneToMany(mappedBy = "engineer",cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
