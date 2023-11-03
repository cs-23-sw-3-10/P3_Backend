package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
public class EquipmentController {


    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository){ //dependency injection
        this.equipmentRepository=equipmentRepository;
    }

    @GetMapping("/AllEquipment")
    public ResponseEntity<?> getAllEquipment() {
        try{
            Iterable<Equipment> equipment=equipmentRepository.findAll();
            boolean isNotEmpty=equipment.iterator().hasNext();
            if (isNotEmpty){
                return new ResponseEntity<Iterable<Equipment>>(equipment, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No equipment found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/EquipmentByType")
    public ResponseEntity<?> getEquipmentByType(@RequestBody Map<String, String> body) {
        try{
            Iterable<Equipment> equipment=equipmentRepository.findAllByType(body.get("type"));
            boolean isNotEmpty=equipment.iterator().hasNext();
            if (isNotEmpty){
                return new ResponseEntity<Iterable<Equipment>>(equipment, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No equipment found with the provided type",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/EquipmentTypes")
    public ResponseEntity<?> getEquipmentTypes() {
        try{
            Iterable<String> types=equipmentRepository.findDistinctTypes();
            boolean isNotEmpty=types.iterator().hasNext();
            if(isNotEmpty){
                return new ResponseEntity<Iterable<String>>(types, HttpStatus.OK);
            }else {
                return new ResponseEntity<String>("No equipment types found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/EquipmentByName")
    public ResponseEntity<?> getEquipmentByName(@RequestBody Map<String, String> body) {
        try{
            Equipment equipment=equipmentRepository.findByName(body.get("name"));
            if(equipment!= null){
                return new ResponseEntity<Equipment>(equipment, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Equipment not found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An error occured: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
