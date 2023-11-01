package sw_10.p3_backend.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    private int scheduleId;
    private boolean isActive;

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
    private ArrayList<BladeProject> bladeProject = new ArrayList<>();

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
    private ArrayList<Conflict> conflicts = new ArrayList<>();

}
