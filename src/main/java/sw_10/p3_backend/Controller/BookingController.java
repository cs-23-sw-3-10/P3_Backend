package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.Logic.BookingLogic;

import java.util.List;

@Controller
public class  BookingController {
    private final BookingLogic bookingLogic;

    public BookingController(BookingLogic bookingLogic){
        this.bookingLogic = bookingLogic;
    }

    /**
     * Fetches all bookings
     * @return all bookings
     */
    @QueryMapping
    public List<Booking> getAllBookings(){
        return bookingLogic.findAll();
    }

    /**
     * Fetches all bookings on a blade task with a certain id
     * @param id
     * @return all bookings that is on a blade task with a certain id
     */
    @QueryMapping
    public List<Booking> BookingByBPId(@Argument Long id){return bookingLogic.getBookingById(id);}
}
