package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.BookingRepository;

import java.util.List;

@Service
public class BookingLogic {

    private final BookingRepository bookingRepository;
    private final int maxAmount = 3;//Temp value for max amount of bookings find a smarter way to do this
    public BookingLogic(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //TODO: Currently does not handle amount and workhours of resource orders add this and optimize saving of bookings
    public void createBookings(List<ResourceOrder> resourceOrders, BladeTask bladeTask) {
        for (ResourceOrder resourceOrder: resourceOrders) {

            List<Equipment> freeEquipment = bookingRepository.findAvailableEquipment(resourceOrder.getStartDate(),
                    resourceOrder.getEndDate(), resourceOrder.getType());

            //TODO: Update to handle all types of equipment
            if (!freeEquipment.isEmpty()){
                //If there is available equipment create a booking using the first available equipment
                Booking newBooking = new Booking(resourceOrder.getStartDate(), resourceOrder.getEndDate(), freeEquipment.get(0), bladeTask);
                bookingRepository.save(newBooking);
            }else {
                //If there is no available equipment create a booking with no equipment and spawn a conflict!
                Booking newBooking = new Booking(resourceOrder.getStartDate(), resourceOrder.getEndDate(), null, bladeTask);
                bookingRepository.save(newBooking);

                conflictHandler(newBooking);

            }
        }
    }

    private void conflictHandler(Booking booking){
        //call conflict logic that will handle the conflict and push it to the database
    }
}
