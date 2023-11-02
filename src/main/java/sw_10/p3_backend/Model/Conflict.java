package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Entity
@Table(name = "conflict")
public class Conflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conflictId;


    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop

}
