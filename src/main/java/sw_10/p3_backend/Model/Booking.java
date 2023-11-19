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

    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeTask bladeTask) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.equipment = equipment;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    //constructor for engineer booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
    }
    //constructor for technician booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Technician technician, BladeTask bladeTask) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.technician = technician;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
    }

    //constructor for engineer booking
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeTask bladeTask) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.engineer = engineer;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
    }
}
