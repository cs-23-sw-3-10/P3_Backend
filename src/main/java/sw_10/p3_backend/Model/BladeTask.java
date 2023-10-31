package sw_10.p3_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "bladeTask")
public class BladeTask {
    private static enum taskType {PRODUCTION, MAINTENANCE, BREAKDOWN, OTHER};//Temp change later!
    private static enum taskStatus {PENDING, ACTIVE, FAILED, FINISHED};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BladeTaskId;
    private int startDate;
    private int endDate;
    private int duration;
    private String taskName;
    private taskType taskType;
    private int attachedPeriod;
    private int attachedTask;
    private taskStatus taskStatus;
    @OneToOne
    @JoinColumn(name = "resourceOrderId")
    private ResourceOrder resourceOrder;
    @ManyToOne
    @JoinColumn(name = "bladeProjectid")
    private BladeProject bladeProject;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
}
