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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isActive;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<BladeProject> bladeProject = new ArrayList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        Schedule clonedSchedule = (Schedule) super.clone();

        clonedSchedule.id = 1;
        // Deep clone bladeProjects
        clonedSchedule.bladeProject = new ArrayList<>();
        for (BladeProject bp : this.bladeProject) {
            clonedSchedule.bladeProject.add(bp.cloneWithSchedule(clonedSchedule)); // Recursive call
        }
        return clonedSchedule;
    }
}
