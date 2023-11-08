package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String testType; //TODO: Change to enum //private taskType taskType;
    private int attachPeriod;
    private int detachPeriod;
    private String taskName;
    private int testRig;
    //private int state; //TODO: Change to enum

    @ManyToOne
    @JoinColumn(name = "bladeProjectId")
    @Getter(AccessLevel.NONE) BladeProject bladeProject; //Ensures getter of will not get stuck in endless recursive loop


    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "bladeTask", cascade = CascadeType.ALL)
    private List<ResourceOrder> resourceOrder = new ArrayList<>();

    public BladeTask(LocalDate startDate, int duration, BladeProject bladeProject){
        setStartDate(startDate);
        setDuration(duration);
        setBladeProject(bladeProject);
    }

    public BladeTask(LocalDate startDate, LocalDate endDate, int duration, String testType, int attachPeriod, int detachPeriod, String taskName, int testRig, BladeProject bladeProject) {
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
