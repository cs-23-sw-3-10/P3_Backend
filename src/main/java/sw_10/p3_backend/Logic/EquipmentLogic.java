package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.Optional;

@Service
public class EquipmentLogic {
    private final EquipmentRepository equipmentRepository;

    EquipmentLogic(EquipmentRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment EquipmentById(Integer id) throws NotFoundException {
        try {
            Optional<Equipment> equipment = equipmentRepository.findById(Long.valueOf(id));
            if (equipment.isEmpty())
                throw new NotFoundException("Equipment not found with id: " + id);
            return equipment.get();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Cannot parse null");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error getting equipment");
        }
    }
}