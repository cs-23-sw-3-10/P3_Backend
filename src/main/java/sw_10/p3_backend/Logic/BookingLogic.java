package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.BookingRepository;

import java.util.List;

@Service
public class BookingLogic {

    private final BookingRepository bookingRepository;

    public BookingLogic(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void createBookings(List<ResourceOrder> resourceOrders) {
        for (ResourceOrder resourceOrder: resourceOrders) {

            System.out.println(bookingRepository.findOverlappingEvents(resourceOrder.getStartDate(),
                    resourceOrder.getEndDate(), resourceOrder.getType()));
            }
    }
}
