package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.Repository.ScheduleRepository;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleLogic {
    private final ScheduleRepository scheduleRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    ScheduleLogic(ScheduleRepository scheduleRepository, BookingRepository bookingRepository){
        this.scheduleRepository = scheduleRepository;
        this.bookingRepository = bookingRepository;
    }

    public Schedule getSchedule(Map<String,Boolean> body){
        return scheduleRepository.findScheduleByIsActive(body.get("state"));
    }

    public List<Booking> getBooking(Map<String,Integer> body){

    String type = getType(body.get("type"));
    List<Booking> bookinglist =  bookingRepository.findOverlappingEvents(body.get("startDate"),body.get("endDate"),type);
    System.out.println(bookinglist.size());
    return bookinglist;
    }

    private String getType(int typeNumber){
        return switch (typeNumber) {
            case 1 -> "hammer";
            case 2 -> "saw";
            default -> "not found";
        };

    }

}
