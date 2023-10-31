package sw_10.p3_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "conflict")
public class Conflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conflictId;

}
