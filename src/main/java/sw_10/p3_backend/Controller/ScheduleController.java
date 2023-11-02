package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Logic.ScheduleLogic;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Schedule;

import java.util.Map;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleLogic scheduleLogic;


    @GetMapping("/getSchedule")
    public ResponseEntity<Schedule> getSchedule(@RequestBody Map<String,Boolean> body){
        return new ResponseEntity<Schedule>(scheduleLogic.getSchedule(body), HttpStatus.OK);
    }

    @GetMapping("/getBookings")
    public ResponseEntity<Iterable<Booking>> getBooking(@RequestBody Map<String, Integer> body){
        return new ResponseEntity<Iterable<Booking>>(scheduleLogic.getBooking(body),HttpStatus.OK);
    }
}
