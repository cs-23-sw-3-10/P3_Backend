package sw_10.p3_backend.Logic;

import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.BookingRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingLogic {

    private final BookingRepository bookingRepository;
    public BookingLogic(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //TODO: Currently does not handle amount and workhours of resource orders add this and optimize saving of bookings
    public void createBookings(List<ResourceOrder> resourceOrders, BladeTask bladeTask) {
        for (ResourceOrder resourceOrder: resourceOrders) {

            //TODO: Find start and end date of booking
            LocalDate bookingStartDate = bookingStartDate(resourceOrder, bladeTask);
            LocalDate bookingEndDate = bookingEndDate(resourceOrder, bladeTask);

            System.out.println(bookingStartDate + " " + bookingEndDate);

            List<Equipment> freeEquipment = bookingRepository.findAvailableEquipment(bookingStartDate, bookingEndDate, resourceOrder.getType());
            System.out.println(Arrays.toString(freeEquipment.toArray()));
            //TODO: Update to handle all types of equipment
            if (!freeEquipment.isEmpty()){
                //If there is available equipment create a booking using the first available equipment
                Booking newBooking = new Booking(bookingStartDate, bookingEndDate, freeEquipment.get(0), bladeTask);
                bookingRepository.save(newBooking);
            }else {
                //If there is no available equipment create a booking with no equipment and spawn a conflict!
                Booking newBooking = new Booking(bookingStartDate, bookingEndDate, null, bladeTask);
                bookingRepository.save(newBooking);

                conflictHandler(newBooking);

            }
        }
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
}
