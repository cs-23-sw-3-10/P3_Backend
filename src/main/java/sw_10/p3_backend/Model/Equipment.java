package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private static List<String> equpmentList;
    private String type;
    private Date calibrationExpirationDate;
    private String name;

    @OneToMany(mappedBy = "equipment",cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

}
