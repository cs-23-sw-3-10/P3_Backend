package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;

import java.util.Map;

@RestController
public class EngineerController {

    private final EngineerRepository engineerRepository;

    @Autowired
    public EngineerController(EngineerRepository engineerRepository){ //dependency injection
        this.engineerRepository=engineerRepository;
    }
    @GetMapping("/AllEngineers")
    public ResponseEntity<?> getAllEngineers() {
        try{
            Iterable<Engineer> engineers=engineerRepository.findAll();
            boolean isNotEmpty=engineers.iterator().hasNext();

            if(isNotEmpty){
                return new ResponseEntity<Iterable<Engineer>>(engineers, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No engineers found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/EngineerByName")
    public ResponseEntity<?> getEngineerByName(@RequestBody Map<String, String> body) {
        try {
            Engineer engineer=engineerRepository.findByName(body.get("name"));
            if(engineer!= null){
                return new ResponseEntity<Engineer>(engineer, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Engineer not found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<String>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
