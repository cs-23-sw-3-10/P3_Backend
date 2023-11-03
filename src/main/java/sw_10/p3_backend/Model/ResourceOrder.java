package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

@Entity
@Table(name = "resourceOrder")
public class ResourceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private int startDate;
    private int endDate;
    private int duration;
    private int workHours;

    @ManyToOne
    @JoinColumn(name = "bladeTaskId")
    @Getter(AccessLevel.NONE) BladeTask bladeTask; //Ensures getter of will not get stuck in endless recursive loop



}
