package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
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

    public Equipment CreateEquipment(String name, String type, String calibrationExpirationDate) {
        validateInput(name, type, calibrationExpirationDate);
        try {
            Equipment equipment = new Equipment();
            equipment.setName(name);
            equipment.setType(type);
            equipment.setCalibrationExpirationDate(LocalDate.parse(calibrationExpirationDate));
            return equipmentRepository.save(equipment);
        } catch (Exception e) {
            throw new RuntimeException("Error creating equipment");
        }
    }

    public List<Equipment> findAvailableEquipment(LocalDate start, LocalDate end, String ResourceName) {
        System.out.println("ResourceName: " + ResourceName);
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        return equipmentRepository.findAvailableEquipment(start, end, ResourceName);
    }

    private void validateInput(String name, String type, String calibrationExpirationDate) {
        if (name == null) {
            throw new InputInvalidException("Name cannot be empty");
        }
        if (equipmentRepository.findByName(name) != null) {
            throw new InputInvalidException("Equipment with that name already exists");
        }
        if (type == null) {
            throw new InputInvalidException("Type cannot be empty");
        }
        if (calibrationExpirationDate == null) {
            throw new InputInvalidException("Calibration expiration date cannot be empty");
        }
        if (LocalDate.parse(calibrationExpirationDate).isBefore(LocalDate.now())) {
            throw new InputInvalidException("Calibration expiration date cannot be in the past");
        }
    }
}