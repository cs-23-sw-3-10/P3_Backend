package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Table(name = "bladeTask")
@NoArgsConstructor
@Setter @Getter
public class BladeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int startDate;
    private int endDate;
    private int duration;
    private String taskName;
    //private taskType taskType;
    private int attachedPeriod;
    private int attachedTask;
    //private taskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "bladeprojectid")
    @Getter(AccessLevel.NONE) BladeProject bladeProject; //Ensures getter of will not get stuck in endless recursive loop


    @OneToMany(mappedBy = "bladeTask",cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "bladeTask",cascade = CascadeType.ALL)
    private ArrayList<ResourceOrder> resourceOrder = new ArrayList<>();

    public BladeTask(int startDate, int duration, BladeProject bladeProject){
        setStartDate(startDate);
        setDuration(duration);
        setBladeProject(bladeProject);
    }

    public String getBladeProject(){
        return this.bladeProject.getBladeName();
    }

}
