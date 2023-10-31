package sw_10.p3_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    private int scheduleId;
    private boolean isActive;



}
