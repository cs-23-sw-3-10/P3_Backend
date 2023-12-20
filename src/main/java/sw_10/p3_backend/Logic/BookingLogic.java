package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BookingRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingLogic {

    private final BookingRepository bookingRepository;
    private final EngineerLogic engineerLogic;
    private final EquipmentLogic equipmentLogic;
    private final TechnicianLogic technicianLogic;
    private final ConflictLogic conflictLogic;


    @Autowired
    public BookingLogic(BookingRepository bookingRepository, EngineerLogic engineerLogic, EquipmentLogic equipmentLogic, TechnicianLogic technicianLogic, ConflictLogic conflictLogic) {
        this.bookingRepository = bookingRepository;
        this.engineerLogic = engineerLogic;
        this.equipmentLogic = equipmentLogic;
        this.technicianLogic = technicianLogic;
        this.conflictLogic = conflictLogic;
    }


    /**
     * Creates bookings based on the resource orders and the bladetask
     * @param resourceOrders List of resource orders
     * @param bladeTask The bladetask that the bookings belong to
     */
    public void createBookings(List<ResourceOrder> resourceOrders, BladeTask bladeTask) {
        int tempconflictcheck = 0;
        for (ResourceOrder resourceOrder: resourceOrders) {

            //Find start and end date of booking based on equipmentAssignmentStatus
            LocalDate bookingStartDate = bookingStartDate(resourceOrder, bladeTask);
            LocalDate bookingEndDate = bookingEndDate(resourceOrder, bladeTask);

            //Handle all different types of resource orders
            switch (resourceOrder.getResourceType().toLowerCase()){
                case "equipment":
                {
                    tempconflictcheck += handleEquipmentBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }
                case "technician":
                {
                    handleTechnicianBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }
                case "engineer":
                {
                    handleEngineerBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }

            }

        }
        if(tempconflictcheck == 0){
            bladeTask.setInConflict(false);
        }
    }

    /**
     * Creates bookings based on the resource orders and the blade project
     * @param resourceOrders List of resource orders
     * @param bladeProject The blade project that the bookings belong to
     */
    public void createBookings(List<ResourceOrder> resourceOrders, BladeProject bladeProject) {
        int tempconflictcheck = 0;
        for (ResourceOrder resourceOrder: resourceOrders) {

            //When you create a booking, the start and end date of the project are null
            LocalDate bookingStartDate = bladeProject.getStartDate();
            LocalDate bookingEndDate = bladeProject.getEndDate();

            //Handle all different types of resource orders
            switch (resourceOrder.getResourceType().toLowerCase()){
                case "equipment":
                {
                    tempconflictcheck += handleEquipmentBooking(resourceOrder, bladeProject, bookingStartDate, bookingEndDate);
                    break;
                }
                case "engineer":
                {
                    handleEngineerBooking(resourceOrder, bladeProject, bookingStartDate, bookingEndDate);
                    break;
                }
            }
        }
        if(tempconflictcheck == 0){
            bladeProject.setInConflict(false);
        }
    }


    /**
     * Handles the creation of bookings with equipment for a blade task
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeTask The bladetask that the booking belongs to
     * @param bookingStartDate The start date of the booking
     * @param bookingEndDate The end date of the booking
     * @return 0 if no conflict was created, 1 if a conflict was created
     */
    private int handleEquipmentBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        //Find available equipment
        String equipmentName = resourceOrder.getResourceName().toLowerCase();
        List<Equipment> freeEquipmentList = equipmentLogic.findAvailableEquipment(bookingStartDate, bookingEndDate, equipmentName);


        if (!freeEquipmentList.isEmpty()){
            //If there is available equipment create a booking using the first available equipment
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, freeEquipmentList.get(0), bladeTask,resourceOrder.getResourceType(), resourceOrder.getResourceName());
            bookingRepository.save(newBooking);
            return 0;
        }else {
            //If there is no available equipment create a booking with no equipment and spawn a conflict!
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, bladeTask ,resourceOrder.getResourceType(), resourceOrder.getResourceName());
            bookingRepository.save(newBooking);

            //conflictHandler(newBooking, bladeTask);

            newBooking.setConflict(conflictHandler(newBooking, bladeTask));

            bookingRepository.save(newBooking);
            return 1;
        }
    }

    /**
     * Handles the creation of bookings with equipment for a blade project
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeProject The blade project that the booking belongs to
     * @param bookingStartDate The start date of the booking
     * @param bookingEndDate The end date of the booking
     * @return 0 if no conflict was created, 1 if a conflict was created
     */
    private int handleEquipmentBooking(ResourceOrder resourceOrder, BladeProject bladeProject, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find available equipment
        String equipmentName = resourceOrder.getResourceName().toLowerCase();
        List<Equipment> freeEquipmentList = equipmentLogic.findAvailableEquipment(bookingStartDate, bookingEndDate, equipmentName);


        if (!freeEquipmentList.isEmpty()){
            //If there is available equipment create a booking using the first available equipment
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, freeEquipmentList.get(0), bladeProject,resourceOrder.getResourceType(), resourceOrder.getResourceName());
            bookingRepository.save(newBooking);
            bladeProject.getBookings().add(newBooking);
            return 0;
        }else {
            //If there is no available equipment create a booking with no equipment and spawn a conflict!
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, bladeProject ,resourceOrder.getResourceType(), resourceOrder.getResourceName());
            bookingRepository.save(newBooking);
            bladeProject.getBookings().add(newBooking);
            //conflictHandler(newBooking, bladeProject);
            return 1;
        }
    }

    /**
     * Handles the creation of bookings with technicians for a blade task
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeTask The blade task that the booking belongs to
     * @param bookingStartDate The start date of the booking
     * @param bookingEndDate The end date of the booking
     */
    private void handleTechnicianBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        //Find the technician that is assigned to the resource order
        Technician technician = technicianLogic.findTechnicians(resourceOrder.getResourceName());

        //Create a new booking with the technician
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate,technician , bladeTask, resourceOrder.getResourceType(), resourceOrder.getResourceName());
        bookingRepository.save(newBooking);

        //update the technicians workhours
        technicianLogic.updateTechnician(technician, resourceOrder.getWorkHours());
    }

    /**
     * Handles the creation of bookings with engineers for a blade task
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeTask The blade task that the booking belongs to
     * @param bookingStartDate The start date of the booking
     * @param bookingEndDate The end date of the booking
     */
    private void handleEngineerBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find the engineer that is assigned to the resource order
        Engineer engineer = engineerLogic.findByName(resourceOrder.getResourceName());

        //Create a new booking with the engineer
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate, engineer, bladeTask, resourceOrder.getResourceType(), resourceOrder.getResourceName());

        //Save the booking to the database
        bookingRepository.save(newBooking);

        //update the engineers workhours
        engineerLogic.updateEngineer(engineer, resourceOrder.getWorkHours());
    }


    /**
     * Handles the creation of bookings with engineers for a blade project
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeProject The blade project that the booking belongs to
     * @param bookingStartDate The start date of the booking
     * @param bookingEndDate The end date of the booking
     */
    private void handleEngineerBooking(ResourceOrder resourceOrder, BladeProject bladeProject, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find the engineer that is assigned to the resource order
        Engineer engineer = engineerLogic.findByName(resourceOrder.getResourceName());

        //Create a new booking with the engineer
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate, engineer, bladeProject, resourceOrder.getResourceType(), resourceOrder.getResourceName());

        //Save the booking to the database
        bookingRepository.save(newBooking);

        //update the engineers workhours
        engineerLogic.updateEngineer(engineer, resourceOrder.getWorkHours());
    }

    /**
     * Finds the start date of a booking based on the equipmentAssignmentStatus of a resource order
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeTask The blade task that the booking belongs to
     * @return The start date of the booking
     */
    private LocalDate bookingStartDate(ResourceOrder resourceOrder, BladeTask bladeTask) {
        //startDate if [true,...]
        if (resourceOrder.getEquipmentAssignmentStatus().get(0)) {
            return bladeTask.getStartDate();
            //startDate if [false,...]
        } else {
            return bladeTask.getStartDate().plusDays(bladeTask.getAttachPeriod());
        }
    }

    /**
     * Finds the end date of a booking based on the equipmentAssignmentStatus of a resource order
     * @param resourceOrder The resource order that the booking is based on
     * @param bladeTask The blade task that the booking belongs to
     * @return The end date of the booking
     */
    private LocalDate bookingEndDate(ResourceOrder resourceOrder, BladeTask bladeTask){
        //endDate if [...,true]
        if(resourceOrder.getEquipmentAssignmentStatus().get(1)){
            return bladeTask.getEndDate();
        }
        //endDate if [...,false]
        else {
            return bladeTask.getEndDate().minusDays(bladeTask.getDetachPeriod());
        }
    }

    /**
     * Handles the creation of a conflict
     * @param booking The booking that the conflict is based on
     * @param bladeTask The blade task that the booking belongs to
     * @return
     */
    private Conflict conflictHandler(Booking booking, BladeTask bladeTask){
        //call conflict logic that will handle the conflict and push it to the database
        Conflict conflict = conflictLogic.createConflict(booking, bladeTask);
        bladeTask.setInConflict(true);
        return conflict;
    }

    /**
     * Removes all bookings (and their conflicts) that belong to a bladetask
     * @param bladeTaskToUpdate The bladetask that the bookings belong to
     */
    public void removeBookingsBladeTask(BladeTask bladeTaskToUpdate) {
        //Finds the bladetasks bookings
        List<Booking> bookings = bookingRepository.findByBladeTask(bladeTaskToUpdate);
        //Deletes the bookings and their conflicts
        conflictLogic.removeConflicts(bookings);
        bookingRepository.deleteAll(bookings);
    }

    /**
     * Removes all bookings that belong to a blade project
     * @param BPId The id of the blade project that the bookings belong to
     */
    public void removeBookingsBladeProject(Long BPId) {
        //Finds the Blade Project
        List<Booking> bookings = bookingRepository.findBookingsByBPId(BPId);

        //Deletes the bookings and their conflicts
        bookingRepository.deleteAll(bookings);
    }

    /**
     * Deletes all bookings on a bladetask and then recreates them
     * @param bladeTask The bladetask that the bookings belong to
     * @return The bladetask with the new bookings
     */
    public BladeTask deleteAndRecreateBookings(BladeTask bladeTask) {
        //Deletes bookings on a bladetask and then recreates them
        removeBookingsBladeTask(bladeTask);
        createBookings(bladeTask.getResourceOrders(), bladeTask);
        return bladeTask;
    }

    /**
     * Deletes all conflicts that are related to the blade task
     * @param bladeTask The blade task that the conflicts are related to
     */
    public void resetRelatedConflicts(BladeTask bladeTask) {
        bladeTask.setRelatedConflicts(new HashSet<>());
    }

    /**
     * This method recalculates the conflicts of a bladetask to make sure that the conflicts are up to date
     * @param bladeTask The bladetask that the conflicts are related to
     */
    public void recalculateConflicts(BladeTask bladeTask) {
        //Finds all bookings that overlaps with the bladetask in regard to dates
        List<Booking> bookings = bookingRepository.findAllByPeriod(bladeTask.getStartDate(), bladeTask.getEndDate());
        //Removes the conflicts from the fetched bookings
        conflictLogic.removeConflicts(bookings);

        //This creates a new conflict, if the booking does not have a specific piece of equipment assigned
        for (Booking booking: bookings) {
            if(booking.fetchEquipment() == null){
                conflictLogic.createConflict(booking, booking.fetchBladeTask());
            }
        }
    }

    /**
     * This method updates the start and end date of all bookings that belong to a blade project
     * @param bladeProject The blade project that the bookings belong to
     * @param startDate The new start date of the bookings
     * @param endDate The new end date of the bookings
     */
    public void updateBookings(BladeProject bladeProject, LocalDate startDate, LocalDate endDate){
        List<Booking> bookings = bladeProject.getBookings();
        for (Booking booking: bookings) {
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            bookingRepository.save(booking);
        }
    }

    /**
     * This method finds a booking based on its id
     * @param id The id of the booking
     * @return The booking with the given id
     */
    public List<Booking> getBookingById(Long id){
        return bookingRepository.findBookingsByBPId(id);
    }

    /**
     * This method finds all bookings in the database
     * @return A list of all bookings in the database
     */
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
}
