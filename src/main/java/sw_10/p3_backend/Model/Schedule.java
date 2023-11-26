package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "schedule")
public class Schedule implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isActive;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<BladeProject> bladeProject = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Conflict> conflicts = new ArrayList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        Schedule clonedSchedule = (Schedule) super.clone();

        clonedSchedule.setId(0);
        clonedSchedule.setBladeProject(new ArrayList<>());
        clonedSchedule.setConflicts(new ArrayList<>());
        clonedSchedule.setActive(false);
        System.out.println("clone" + clonedSchedule.getId());
        return clonedSchedule;
    }
}
