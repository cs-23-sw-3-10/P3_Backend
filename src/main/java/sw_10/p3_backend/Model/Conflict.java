package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "conflict")
public class Conflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //public enum conflictType { OVERLAP, PERSONNEL, EQUIPMENT }
    private int type;
    private String message;


    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop

}
