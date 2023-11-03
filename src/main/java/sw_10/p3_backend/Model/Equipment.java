package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentId;
    private static List<String> equpmentList;
    private String type;
    private String name;

    private Date calibrationExpirationDate;

    @OneToMany(mappedBy = "equipment",cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

}
