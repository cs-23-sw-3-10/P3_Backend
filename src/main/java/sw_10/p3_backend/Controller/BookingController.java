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

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    BookingLogic bookingLogic;

    @QueryMapping
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    @QueryMapping
    public List<Booking> BookingByBPId(@Argument Long id){return bookingLogic.getBookingById(id);}
}
