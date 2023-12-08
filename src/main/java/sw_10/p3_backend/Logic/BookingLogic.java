package sw_10.p3_backend.Logic;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.Repository.ConflictRepository;

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

    //TODO: Currently does not handle amount and workhours of resource orders add this and optimize saving of bookings
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
                    System.out.println("Equipment booking");
                    tempconflictcheck += handleEquipmentBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }
                case "technician":
                {
                    System.out.println("Technician booking");
                    handleTechnicianBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }
                case "engineer":
                {
                    System.out.println("Engineer booking");
                    handleEngineerBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
                    break;
                }

            }

        }
        if(tempconflictcheck == 0){
            bladeTask.setInConflict(false);
        }
    }

    public void createBookings(List<ResourceOrder> resourceOrders, BladeProject bladeProject) {
        int tempconflictcheck = 0;
        for (ResourceOrder resourceOrder: resourceOrders) {
            System.out.println("Creating bookings for" + resourceOrder);

            //When you create a booking, the start and end date of the project are null
            LocalDate bookingStartDate = bladeProject.getStartDate();
            LocalDate bookingEndDate = bladeProject.getEndDate();

            //Handle all different types of resource orders
            switch (resourceOrder.getResourceType().toLowerCase()){
                case "equipment":
                {
                    System.out.println("Equipment booking");
                    tempconflictcheck += handleEquipmentBooking(resourceOrder, bladeProject, bookingStartDate, bookingEndDate);
                    break;
                }
                case "engineer":
                {
                    System.out.println("Engineer booking");
                    handleEngineerBooking(resourceOrder, bladeProject, bookingStartDate, bookingEndDate);
                    break;
                }

            }
        }
        if(tempconflictcheck == 0){
            bladeProject.setInConflict(false);
        }
    }

    //TODO: Refactor the handlers for bookings to be more generic and remove duplicate code (maybe use a factory pattern)
    private int handleEquipmentBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        System.out.printf("Booking start date: %s, Booking end date: %s  :%s\n ", bookingStartDate, bookingEndDate,resourceOrder.getResourceName());
        //Find available equipment
        String equipmentName = resourceOrder.getResourceName().toLowerCase();
        List<Equipment> freeEquipmentList = equipmentLogic.findAvailableEquipment(bookingStartDate, bookingEndDate, equipmentName);
        System.out.println(freeEquipmentList);
        System.out.println("Number of free equipment: " + freeEquipmentList.size());


        if (!freeEquipmentList.isEmpty()){
            //If there is available equipment create a booking using the first available equipment
            System.out.println("Creating booking with equipment: " + freeEquipmentList.get(0).getName());
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

    private int handleEquipmentBooking(ResourceOrder resourceOrder, BladeProject bladeProject, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        System.out.printf("Booking start date: %s, Booking end date: %s  :%s\n ", bookingStartDate, bookingEndDate,resourceOrder.getResourceName());
        //Find available equipment
        String equipmentName = resourceOrder.getResourceName().toLowerCase();
        List<Equipment> freeEquipmentList = equipmentLogic.findAvailableEquipment(bookingStartDate, bookingEndDate, equipmentName);
        System.out.println(freeEquipmentList);
        System.out.println("Number of free equipment: " + freeEquipmentList.size());


        if (!freeEquipmentList.isEmpty()){
            //If there is available equipment create a booking using the first available equipment
            System.out.println("Creating booking with equipment: " + freeEquipmentList.get(0).getName());
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

    private void handleTechnicianBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        //Find the technician that is assigned to the resource order
        Technician technician = technicianLogic.findTechnicians(resourceOrder.getResourceName());

        //Create a new booking with the technician
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate,technician , bladeTask, resourceOrder.getResourceType(), resourceOrder.getResourceName());
        bookingRepository.save(newBooking);

        //update the technicians workhours
        technicianLogic.updateTechnician(technician, resourceOrder.getWorkHours());
    }

    private void handleEngineerBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find the engineer that is assigned to the resource order
        Engineer engineer = engineerLogic.findByName(resourceOrder.getResourceName());

        //Create a new booking with the engineer
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate, engineer, bladeTask, resourceOrder.getResourceType(), resourceOrder.getResourceName());

        System.out.println(newBooking);

        //Save the booking to the database
        bookingRepository.save(newBooking);

        //update the engineers workhours
        engineerLogic.updateEngineer(engineer, resourceOrder.getWorkHours());
    }


    private void handleEngineerBooking(ResourceOrder resourceOrder, BladeProject bladeProject, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find the engineer that is assigned to the resource order
        Engineer engineer = engineerLogic.findByName(resourceOrder.getResourceName());

        //Create a new booking with the engineer
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate, engineer, bladeProject, resourceOrder.getResourceType(), resourceOrder.getResourceName());

        System.out.println(newBooking);

        //Save the booking to the database
        bookingRepository.save(newBooking);

        //update the engineers workhours
        engineerLogic.updateEngineer(engineer, resourceOrder.getWorkHours());
    }

    //TODO: ultra stupid logic for finding start and end date of booking refactor plox
    private LocalDate bookingStartDate(ResourceOrder resourceOrder, BladeTask bladeTask) {
        //startDate if [true,...]
        if (resourceOrder.getEquipmentAssignmentStatus().get(0)) {
            return bladeTask.getStartDate();
            //startDate if [false,...]
        } else {
            return bladeTask.getStartDate().plusDays(bladeTask.getAttachPeriod());
        }
    }
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
    private Conflict conflictHandler(Booking booking, BladeTask bladeTask){
        //call conflict logic that will handle the conflict and push it to the database
        System.out.println("Creating Conflict");
        Conflict conflict = conflictLogic.createConflict(booking, bladeTask);
        bladeTask.setInConflict(true);
        return conflict;
    }

    private void createAndSaveBooking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask, Object bookedResource) {
        //Booking newBooking = new Booking(bookingStartDate, bookingEndDate, (Equipment) bookedResource, bladeTask, resourceOrder.getResourceType());
        //bookingRepository.save(newBooking);
    }

    public void removeBookings(BladeTask bladeTaskToUpdate) {
        //Finds the bladetasks bookings
        List<Booking> bookings = bookingRepository.findByBladeTask(bladeTaskToUpdate);
        System.out.println(bookings);
        //Deletes the bookings and their conflicts
        conflictLogic.removeConflicts(bookings);
        bookingRepository.deleteAll(bookings);
    }



    public BladeTask deleteAndRecreateBookings(BladeTask bladeTask) {
        //Deletes bookings on a bladetask and then recreates them
        removeBookings(bladeTask);
        createBookings(bladeTask.getResourceOrders(), bladeTask);
        return bladeTask;
    }

    public void resetRelatedConflicts(BladeTask bladeTaskToUpdate) {
        bladeTaskToUpdate.setRelatedConflicts(new HashSet<>());
    }

    public void recalculateConflicts(BladeTask bladeTaskToUpdate) {
        //Finds all bookings that overlaps with the bladetask in regard to dates
        List<Booking> bookings = bookingRepository.findAllByPeriod(bladeTaskToUpdate.getStartDate(), bladeTaskToUpdate.getEndDate());
        //Removes the conflicts from the fetched bookings
        conflictLogic.removeConflicts(bookings);

        //This creates a new conflict, if the booking does not have a specific piece of equipment assigned
        for (Booking booking: bookings) {
            if(booking.fetchEquipment() == null){
                conflictLogic.createConflict(booking, booking.fetchBladeTask());
            }
        }
    }

    public void updateBookings(BladeProject bladeProject, LocalDate startDate, LocalDate endDate){
        List<Booking> bookings = bladeProject.getBookings();
        for (Booking booking: bookings) {
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            bookingRepository.save(booking);
        }
    }

    public List<Booking> getBookingById(Long id){
        return bookingRepository.findBookingsByBPId(id);
    }
}
