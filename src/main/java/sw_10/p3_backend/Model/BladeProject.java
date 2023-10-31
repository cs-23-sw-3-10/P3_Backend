package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bladeProject")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BladeProject {
    private static String[] customerList;
    private static String[] projectLeaderList;
    private static BladeProject[] bladeProjectList; ;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bladeProjectId;
    private Date startDate;
    private Date endDate;
    private String bladeName;
    private String projectLeader;
    private String customer;

    @OneToMany(mappedBy = "bladeProject",cascade = CascadeType.ALL)
    private List<BladeTask> bladeTasks = new ArrayList<>();

    public BladeProject(String bladeName, String projectLeader, String customer) {
        setBladeName(bladeName);
        setProjectLeader(projectLeader);
        setCustomer(customer);
    }
}
