package sw_10.p3_backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class  BookingController {
    @GetMapping("/api/v1/Bookings/hello")
    public String hello(){
        System.out.println("hello");
        return "123";
    }
}
