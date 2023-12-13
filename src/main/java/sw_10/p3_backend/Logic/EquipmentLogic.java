package sw_10.p3_backend.Logic;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Engineer;
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

    /**
     * This method finds an equipment by its id
     * @param id the id of the equipment you wish to find
     * @return  the equipment with the given id
     * @throws NotFoundException
     */
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

    /**
     * Thi method creates a new equipment if it does not already exist, otherwise it updates the existing equipment
     * @param name the name of the equipment
     * @param type the type of the equipment
     * @param calibrationExpirationDate the calibration expiration date of the equipment
     * @return the equipment that was created or updated
     */
    public Equipment CreateEquipment(String name, String type, String calibrationExpirationDate) {
        validateInput(name, type, calibrationExpirationDate);
        //Finds the equipment by name if it exists
        Equipment equipment =  equipmentRepository.findByName(name);
        try {
            //Checks if the equipment exists, if it does, update it, otherwise create a new equipment
            if (equipment != null) {
                equipment.setCalibrationExpirationDate(LocalDate.parse(calibrationExpirationDate));
                System.out.println("Equipment updated");
                return equipmentRepository.save(equipment);
            }
            Equipment newEquipment = new Equipment();
            newEquipment.setName(name);
            newEquipment.setType(type);
            newEquipment.setCalibrationExpirationDate(LocalDate.parse(calibrationExpirationDate));
            return equipmentRepository.save(newEquipment);
        } catch (Exception e) {
            throw new RuntimeException("Error creating equipment");
        }
    }

    /**
     * This method finds all equipment that does not have a booking in the given time period
     * @param start the start of the time period
     * @param end  the end of the time period
     * @param ResourceName the name of the equipment you wish to find
     * @return the list of equipment that does not have a booking in the given time period
     */
    public List<Equipment> findAvailableEquipment(LocalDate start, LocalDate end, String ResourceName) {
        System.out.println("ResourceName: " + ResourceName);
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        return equipmentRepository.findAvailableEquipment(start, end, ResourceName);
    }

    /**
     * This method validates the input for equipment
     * @param name the name of the equipment
     * @param type the type of the equipment
     * @param calibrationExpirationDate the calibration expiration date of the equipment
     */
    private void validateInput(String name, String type, String calibrationExpirationDate) {
        if (name == null) {
            throw new InputInvalidException("Name cannot be empty");
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

    /**
     * This method finds all unique equipment types
     * @return the list of unique equipment types
     */
    public List<String> getEquipmentTypes(){
        return equipmentRepository.getEquipmentTypes();
    }

    //TODO Ideally we would check if the equipment is in use, if so, then we would check if there's enough equipment to cover the BT and then delete the equipment.

    /**
     * This method deletes an equipment by its name if it does not have any bookings
     * @param name the name of the equipment you wish to delete
     * @return the equipment that was deleted
     */
    public Equipment deleteEquipment(String name) {
        System.out.println(name);
        try {
            //Finds the equipment by name if it exists
            Equipment equipment = equipmentRepository.findByName(name);

            //Current implementation: If a booking with the specific equipment ID is found, throw exception.
            if (equipment != null) {
                if (!equipment.getBookings().isEmpty()) {
                    throw new InputInvalidException("Cannot delete equipment that is assigned to a task.");
                }
                equipmentRepository.delete(equipment);
                return equipment;
            } else {
                throw new NotFoundException("Equipment not found with name: " + name);
            }
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting equipment");
        }
    }

    /**
     * This method finds all equipment
     * @return the list of all equipment
     */
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    /**
     * This method finds all equipment of a certain type
     * @param type the type of the equipment you wish to find
     * @return the list of equipment of the given type
     */
    public List<Equipment> findAllByType(String type) {
        return equipmentRepository.findAllByType(type);
    }
}