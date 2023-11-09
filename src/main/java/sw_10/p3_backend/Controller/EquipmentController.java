package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.EquipmentLogic;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.IdNotFoundException;

import java.util.List;


@Controller
public class EquipmentController {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentLogic equipmentLogic;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository, EquipmentLogic equipmentLogic) { //dependency injection
        this.equipmentRepository = equipmentRepository;
        this.equipmentLogic = equipmentLogic;
    }

    @QueryMapping
    public List<Equipment> AllEquipment() {
        return equipmentRepository.findAll();
    }

    @QueryMapping
    public List<Equipment> EquipmentByType(@Argument String type) {
        return equipmentRepository.findAllByType(type); //consider adding handling for nothing found
    }

    @QueryMapping
    public Equipment EquipmentById(@Argument Integer id) {
        return equipmentLogic.EquipmentById(id);
    }
}