package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.IdNotFoundException;

import java.util.List;


@Controller
public class EquipmentController {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository){ //dependency injection
        this.equipmentRepository=equipmentRepository;
    }


    @QueryMapping
    public List<Equipment> AllEquipment(){
        return equipmentRepository.findAll();
    }

    @QueryMapping
    public List<Equipment> EquipmentByType(@Argument String type){
            return equipmentRepository.findAllByType(type); //consider adding handling for nothing found
    }

    @QueryMapping
    public Equipment EquipmentById(@Argument Integer id){
        try{
        return equipmentRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IdNotFoundException("No equipment found with id:" + id));//Consider adding logic layer and move error handling
    }catch (IdNotFoundException e)
    {
        System.out.println("Equipment id not found: " + e.getMessage());//Maybe add proper logging
        throw e;
    }catch (Exception e) {
        throw new RuntimeException(e);
    }//consider adding handling for nothing found
    }
}