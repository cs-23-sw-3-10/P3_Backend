package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity//tells Hibernate to make a table out of this class
@Table(name = "blade_project")
@Getter @Setter //Lombok annotations to generate getters and setters
@AllArgsConstructor //Lombok annotation to generate constructor with all arguments
@NoArgsConstructor
public class BladeProject {
    private static String[] customerList;
    private static String[] projectLeaderList;
    private static BladeProject[] bladeProjectList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//tells Hibernate to use auto increment
    private Long id;
    private Date startDate;
    private Date endDate;
    private String bladeName;
    private String projectLeader;
    private String customer;

    @OneToMany(mappedBy = "bladeProject",cascade = CascadeType.ALL)
    private List<BladeTask> bladeTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop


    public BladeProject(String bladeName, String projectLeader, String customer) {
        setBladeName(bladeName);
        setProjectLeader(projectLeader);
        setCustomer(customer);
    }
}
