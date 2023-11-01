package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;
    private boolean isActive;

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
    private List<BladeProject> bladeProject = new ArrayList<>();

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
    private List<Conflict> conflicts = new ArrayList<>();

}
