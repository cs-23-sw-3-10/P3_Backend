package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "booking")
public class Booking implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String resourceType;
    private String resourceName;
    private int workHours;

    @ManyToOne
    @JoinColumn(name = "bladeTaskId")
    @Getter(AccessLevel.NONE) private BladeTask bladeTask;
    @ManyToOne
    @JoinColumn(name = "bladeProjectId")
    @Getter(AccessLevel.NONE) private BladeProject bladeProject;
    @ManyToOne
    @JoinColumn(name = "engineerId")
    @Getter(AccessLevel.NONE) private Engineer engineer;
    @ManyToOne
    @JoinColumn(name = "technicianId")
    @Getter(AccessLevel.NONE) private Technician technician;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    @Getter(AccessLevel.NONE) private Equipment equipment;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE) private Conflict conflict;


    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.equipment = equipment;
        this.bladeTask = bladeTask;
    }

    //Blade Task - Technician Booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Technician technician, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType;
        this.technician = technician;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    //Blade Task - Engineer Booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType;
        this.engineer = engineer;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    //Blade Task - Empty Booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeTask = bladeTask;
        this.resourceType = resourceType;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    //Blade Project - Equipment Booking
    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.equipment = equipment;
        this.bladeProject = bladeProject;
    }

    //Blade Project - Engineer Booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType;
        this.engineer = engineer;
        this.bladeProject = bladeProject;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    //Blade Project - Empty Booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeProject = bladeProject;
        this.resourceType = resourceType;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    public BladeTask fetchBladeTask(){
        return bladeTask;
    }

    @Override
    public Booking clone() throws CloneNotSupportedException {
        Booking cloned = (Booking) super.clone();

        // Reset the ID to indicate a new entity
        cloned.id = 0;
        // Deep clone bladeTasks
        //cloned.bladeTask = this.bladeTask.clone();
        if(this.conflict != null){
            cloned.conflict = this.conflict.clone(cloned);
        }

        return cloned;
    }

    public Equipment fetchEquipment() {
        return equipment;
    }
}
