package sw_10.p3_backend.Logic;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BookingRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingLogic {

    private final BookingRepository bookingRepository;
    private final EngineerLogic engineerLogic;
    private final EquipmentLogic equipmentLogic;
    private final TechnicianLogic technicianLogic;

    @Autowired
    public BookingLogic(BookingRepository bookingRepository, EngineerLogic engineerLogic, EquipmentLogic equipmentLogic, TechnicianLogic technicianLogic) {
        this.bookingRepository = bookingRepository;
        this.engineerLogic = engineerLogic;
        this.equipmentLogic = equipmentLogic;
        this.technicianLogic = technicianLogic;
    }

    //TODO: Currently does not handle amount and workhours of resource orders add this and optimize saving of bookings
    public void createBookings(List<ResourceOrder> resourceOrders, BladeTask bladeTask) {
        for (ResourceOrder resourceOrder: resourceOrders) {



            //Find start and end date of booking based on equipmentAssignmentStatus
            LocalDate bookingStartDate = bookingStartDate(resourceOrder, bladeTask);
            LocalDate bookingEndDate = bookingEndDate(resourceOrder, bladeTask);

            //Handle all different types of resource orders
            switch (resourceOrder.getResourceType().toLowerCase()){
                case "equipment":
                {
                    handleEquipmentBooking(resourceOrder, bladeTask, bookingStartDate, bookingEndDate);
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
    }

    private void handleEquipmentBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        System.out.printf("Booking start date: %s, Booking end date: %s  :%s\n ", bookingStartDate, bookingEndDate,resourceOrder.getResourceName());
        //Find available equipment
        List<Equipment> freeEquipmentList = equipmentLogic.findAvailableEquipment(bookingStartDate, bookingEndDate, resourceOrder.getResourceName());

        System.out.println("Number of free equipment: " + freeEquipmentList.size());

        //TODO: Update to handle all types of equipment
        if (!freeEquipmentList.isEmpty()){
            //If there is available equipment create a booking using the first available equipment
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, freeEquipmentList.get(0), bladeTask,resourceOrder.getResourceType());
            bookingRepository.save(newBooking);
        }else {
            //If there is no available equipment create a booking with no equipment and spawn a conflict!
            Booking newBooking = new Booking(bookingStartDate, bookingEndDate, bladeTask);
            bookingRepository.save(newBooking);

            conflictHandler(newBooking);
            throw new RuntimeException("No available equipment");
        }
    }

    private void handleTechnicianBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        //Find the technician that is assigned to the resource order
        Technician technician = technicianLogic.findTechnicians(resourceOrder.getResourceName());

        //Create a new booking with the technician
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate,technician , bladeTask, resourceOrder.getResourceType());
        bookingRepository.save(newBooking);

        //update the technicians workhours
        technicianLogic.updateTechnician(technician, resourceOrder.getWorkHours());
    }

    private void handleEngineerBooking(ResourceOrder resourceOrder, BladeTask bladeTask, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        //Find the engineer that is assigned to the resource order
        Engineer engineer = engineerLogic.findByName(resourceOrder.getResourceName());

        //Create a new booking with the engineer
        Booking newBooking = new Booking(bookingStartDate, bookingEndDate, engineer, bladeTask, resourceOrder.getResourceType());

        System.out.println(newBooking);

        //Save the booking to the database
        bookingRepository.save(newBooking);

        //update the engineers workhours
        //engineerLogic.updateEngineer(engineer, resourceOrder.getWorkHours());
    }

    //TODO: ultra stupid logic for finding start and end date of booking refactor plox
    private LocalDate bookingStartDate(ResourceOrder resourceOrder, BladeTask bladeTask){
        //startDate if [ture,...,...]
        if(resourceOrder.getEquipmentAssignmentStatus().get(0)){
            return bladeTask.getStartDate();
        //startDate if [false,true,...]
        } else if (!resourceOrder.getEquipmentAssignmentStatus().get(0) && resourceOrder.getEquipmentAssignmentStatus().get(1)){
            return bladeTask.getStartDate().plusDays(bladeTask.getAttachPeriod());
        }
        //startDate if [false,false,true]
        else if (!resourceOrder.getEquipmentAssignmentStatus().get(0) && !resourceOrder.getEquipmentAssignmentStatus().get(1) && resourceOrder.getEquipmentAssignmentStatus().get(2)){
            return bladeTask.getEndDate().minusDays(bladeTask.getDetachPeriod());
        }
        return null;
    }
    private LocalDate bookingEndDate(ResourceOrder resourceOrder, BladeTask bladeTask){
        //endDate if [...,...,true]
        if(resourceOrder.getEquipmentAssignmentStatus().get(2)){
            return bladeTask.getEndDate();
        }
        //endDate if [...,true,false]
        else if (!resourceOrder.getEquipmentAssignmentStatus().get(2) && resourceOrder.getEquipmentAssignmentStatus().get(1)){
            return bladeTask.getEndDate().minusDays(bladeTask.getDetachPeriod());
        }
        //endDate if [true,false,false]
        else if (resourceOrder.getEquipmentAssignmentStatus().get(0) && !resourceOrder.getEquipmentAssignmentStatus().get(1) && !resourceOrder.getEquipmentAssignmentStatus().get(2)){
            return bladeTask.getStartDate().plusDays(bladeTask.getAttachPeriod());
        }
        return null;
    }
    private void conflictHandler(Booking booking){
        //call conflict logic that will handle the conflict and push it to the database
    }

    private void createAndSaveBooking(LocalDate bookingStartDate, LocalDate bookingEndDate, BladeTask bladeTask, Object bookedResource) {
        //Booking newBooking = new Booking(bookingStartDate, bookingEndDate, (Equipment) bookedResource, bladeTask, resourceOrder.getResourceType());
        //bookingRepository.save(newBooking);
    }
}
