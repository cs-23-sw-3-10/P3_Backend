package sw_10.p3_backend.Model;

import jakarta.persistence.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    private int equipmentId;
    private static List<String> equpmentList;
    private String type;

    private Date calibrationExpirationDate;

    @OneToMany(mappedBy = "equipment",cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings = new ArrayList<>();

}
