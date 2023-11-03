package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;

import java.util.Map;

@RestController
public class TechnicianController {
    private final TechnicianRepository technicianRepository;

    @Autowired
    public TechnicianController(TechnicianRepository technicianRepository){
        this.technicianRepository=technicianRepository;
    }

    @GetMapping("/AllTechnicians")
    public ResponseEntity<?> getAllTechnicians() {
        try {
            Iterable<Technician> technicians=technicianRepository.findAll();
            boolean isNotEmpty=technicians.iterator().hasNext();
            if(isNotEmpty){
                return new ResponseEntity<Iterable<Technician>>(technicians, HttpStatus.OK);
            }else {
                return new ResponseEntity<String>("No technicians found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<String>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/TechnicianByType")
    public ResponseEntity<?> getTechnicianByType(@RequestBody Map<String, String> body) {
        try{
            Iterable<Technician> technicians=technicianRepository.findAll();
            boolean isNotEmpty=technicians.iterator().hasNext();
            if(isNotEmpty){
                return new ResponseEntity<Iterable<Technician>>(technicians, HttpStatus.OK);
            }else {
                return new ResponseEntity<String>("No technicians of that type found",HttpStatus.NOT_FOUND);
            }

        } catch(Exception e){
            return new ResponseEntity<String>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
