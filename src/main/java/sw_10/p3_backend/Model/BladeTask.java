package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "bladeTask")
@NoArgsConstructor
@Setter @Getter
public class BladeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date startDate;
    private Date endDate;

    private int duration;

    private String testType;
    private int attachPeriod;
    private int detachPeriod;
    private String taskName;
    private int testRig;

    public enum taskState { NOT_STARTED, IN_PROGRESS, COMPLETED }
    private taskState state;

    @ManyToOne
    @JoinColumn(name = "bladeProjectId")
    @Getter(AccessLevel.NONE) BladeProject bladeProject; //Ensures getter of will not get stuck in endless recursive loop


    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<ResourceOrder> resourceOrder = new ArrayList<>();

    public BladeTask(Date startDate, Date endDate, BladeProject bladeProject){
        setStartDate(startDate);
        setDuration(duration);
        setBladeProject(bladeProject);
    }

    public BladeTask(Date startDate, Date endDate, int duration, String testType, int attachPeriod, int detachPeriod, String taskName, int testRig, BladeProject bladeProject) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.testType = testType;
        this.attachPeriod = attachPeriod;
        this.detachPeriod = detachPeriod;
        this.taskName = taskName;
        this.testRig = testRig;
        this.bladeProject = bladeProject;
    }
}
