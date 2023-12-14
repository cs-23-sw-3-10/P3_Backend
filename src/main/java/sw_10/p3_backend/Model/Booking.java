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


    /**
     * Constructor for booking of equipment for blade task
     * @param startDate Start date of booking
     * @param endDate End date of booking
     * @param equipment Equipment to be booked
     * @param bladeTask Blade task the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.resourceType = resourceType.toLowerCase();
        this.resourceName = resourceName.toLowerCase();
        this.equipment = equipment;
        this.bladeTask = bladeTask;
    }

    /**
     * Constructor for booking of technicians for blade task
     * @param bookingStartDate  Start date of booking
     * @param bookingEndDate End date of booking
     * @param technician Technician to be booked
     * @param bladeTask Blade task the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Technician technician, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType.toLowerCase();
        this.technician = technician;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName.toLowerCase();
    }

    /**
     * Constructor for booking of an engineer for blade task
     * @param bookingStartDate Start date of booking
     * @param bookingEndDate End date of booking
     * @param engineer Engineer to be booked
     * @param bladeTask Blade task the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType.toLowerCase();
        this.engineer = engineer;
        this.bladeTask = bladeTask;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName.toLowerCase();
    }

    /**
     * Constructor for empty booking for blade task
     * @param bookingStartDate Start date of booking
     * @param bookingEndDate End date of booking
     * @param bladeTask Blade task the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeTask = bladeTask;
        this.resourceType = resourceType.toLowerCase();
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName.toLowerCase();
    }

    /**
     * Constructor for booking of equipment for blade project
     * @param startDate Start date of booking
     * @param endDate End date of booking
     * @param equipment Equipment to be booked
     * @param bladeProject Blade project the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate startDate, LocalDate endDate, Equipment equipment, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.resourceType = resourceType.toLowerCase();
        this.resourceName = resourceName.toLowerCase();
        this.equipment = equipment;
        this.bladeProject = bladeProject;
    }

    /**
     * Constructor for booking of engineers for blade project
     * @param bookingStartDate Start date of booking
     * @param bookingEndDate End date of booking
     * @param engineer Engineer to be booked
     * @param bladeProject Blade project the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, Engineer engineer, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.resourceType = resourceType.toLowerCase();
        this.engineer = engineer;
        this.bladeProject = bladeProject;
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName.toLowerCase();
    }

    /**
     * Constructor for empty booking for a blade project
     * @param bookingStartDate Start date of booking
     * @param bookingEndDate End date of booking
     * @param bladeProject Blade project the booking is for
     * @param resourceType Type of resource to be booked
     * @param resourceName Name of resource to be booked
     */
    public Booking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeProject bladeProject, String resourceType, String resourceName) {
        this.startDate = bookingStartDate;
        this.endDate = bookingEndDate;
        this.bladeProject = bladeProject;
        this.resourceType = resourceType.toLowerCase();
        this.duration = (int) ChronoUnit.DAYS.between(bookingStartDate, bookingEndDate);
        this.resourceName = resourceName.toLowerCase();
    }

    public BladeTask fetchBladeTask(){
        return bladeTask;
    }

    /**
     * Method to clone a booking
     * @return cloned booking
     * @throws CloneNotSupportedException
     */
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
