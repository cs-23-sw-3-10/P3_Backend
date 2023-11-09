package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.IdNotFoundException;

@Service
public class EquipmentLogic {
    private final EquipmentRepository equipmentRepository;

    EquipmentLogic(EquipmentRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment EquipmentById(Integer id) throws IdNotFoundException {
        // Custom Exception Handling
        return equipmentRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IdNotFoundException("No equipment found with id: " + id));
    }
}