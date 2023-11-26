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
public class Booking {
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
    @JoinColumn(name = "engineerId")
    @Getter(AccessLevel.NONE) private Engineer engineer;
    @ManyToOne
    @JoinColumn(name = "technicianId")
    @Getter(AccessLevel.NONE) private Technician technician;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    @Getter(AccessLevel.NONE) private Equipment equipment;

    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.resourceType = resourceType;
        this.equipment = equipment;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(startDate, endDate);
        this.resourceName = resourceName;
    }

    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeTask = bladeTask;
        this.resourceType = resourceType;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }
    //constructor for technician booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Technician technician, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType;
        this.technician = technician;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    //constructor for engineer booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType;
        this.engineer = engineer;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName;
    }

    public BladeTask fetchBladeTask(){
        return bladeTask;
    }
}
