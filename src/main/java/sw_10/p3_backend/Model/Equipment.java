package sw_10.p3_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @OneToMany(mappedBy = "equipment")
    private List<Booking> bookingList;

}
