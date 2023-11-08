package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity//tells Hibernate to make a table out of this class
@Table(name = "bladeProject")
@Getter @Setter //Lombok annotations to generate getters and setters
@AllArgsConstructor //Lombok annotation to generate constructor with all arguments
@NoArgsConstructor
public class BladeProject {
    private static String[] customerList;
    private static String[] projectLeaderList;
    private static BladeProject[] bladeProjectList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String customer;
    private String projectLeader;
    private String projectName;

    @OneToMany(mappedBy = "bladeProject", cascade = CascadeType.ALL)
    private List<BladeTask> bladeTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop


    public BladeProject(Schedule schedule, String projectName,String customer, String projectLeader) {
        setProjectName(projectName);
        setProjectLeader(projectLeader);
        setCustomer(customer);
        setSchedule(schedule);
    }

    public BladeProject(String projectName){
        setProjectName(projectName);
    }
}
