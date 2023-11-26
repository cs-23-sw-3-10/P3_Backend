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
public class BladeProject implements Cloneable {
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
    private String color;

    @OneToMany(mappedBy = "bladeProject", cascade = CascadeType.ALL)
    private List<BladeTask> bladeTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @Getter(AccessLevel.NONE) Schedule schedule; //Ensures getter of will not get stuck in endless recursive loop


    public BladeProject(Schedule schedule, String projectName,String customer, String projectLeader, String color) {
        setSchedule(schedule);
        setProjectName(projectName);
        setProjectLeader(projectLeader);
        setCustomer(customer);
        setColor(color);
    }

    public BladeProject(String projectName){
        setProjectName(projectName);
    }

        // ...

    public BladeProject cloneWithSchedule(Schedule newSchedule) {
        try {
            BladeProject cloned = (BladeProject) super.clone();

            // Reset the ID to indicate a new entity
            cloned.id = 0;

            // Associate the cloned BladeProject with the provided Schedule
            cloned.schedule = newSchedule;

            // Deep clone bladeTasks
            cloned.bladeTasks = new ArrayList<>();
            for (BladeTask task : this.bladeTasks) {
                // Clone the task and associate it with the cloned BladeProject
                BladeTask clonedTask = task.clone(); // Make sure BladeTask has a proper clone method
                clonedTask.setBladeProject(cloned);
                cloned.bladeTasks.add(clonedTask);
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            // Handle the exception, possibly rethrow as a runtime exception
            throw new RuntimeException(e);
        }
    }
    }

