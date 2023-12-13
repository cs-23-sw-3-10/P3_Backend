package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import sw_10.p3_backend.Logic.EquipmentLogic;
import sw_10.p3_backend.Model.Equipment;
import sw_10.p3_backend.Repository.EquipmentRepository;
import sw_10.p3_backend.exception.InputInvalidException;

import java.security.Principal;
import java.util.List;


@Controller
public class EquipmentController {
    private final EquipmentLogic equipmentLogic;

    public EquipmentController(EquipmentLogic equipmentLogic) { //dependency injection
        this.equipmentLogic = equipmentLogic;
    }


    /**
     * This method fetches all equipment
     * @return all equipment
     */
    @QueryMapping
    public List<Equipment> AllEquipment() {
        return equipmentLogic.findAll();
    }

    /**
     * This method fetches all equipment of a certain type
     * @param type
     * @return all equipment of a certain type
     */
    @QueryMapping
    public List<Equipment> EquipmentByType(@Argument String type) {
        return equipmentLogic.findAllByType(type); //consider adding handling for nothing found
    }


    /**
     * This method fetches an equipment with a certain id
     * @param id
     * @return the equipment with the passed id
     */
    @QueryMapping
    public Equipment EquipmentById(@Argument Integer id) {
        try {
            if (id == null) {
                throw new InputInvalidException("cannot parse null");
            }
            return equipmentLogic.EquipmentById(id);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * This method fetches all the types of equipment
     * @return the types of equipment
     */
    @QueryMapping
    public List<String> GetEquipmentTypes() {
        return equipmentLogic.getEquipmentTypes();
    }


    /**
     * This method creates a new equipment object
     * @param name the name of the equipment
     * @param type the type of the equiopment
     * @param calibrationExpirationDate
     * @return the created equipment
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Equipment CreateEquipment(@Argument String name, @Argument String type, @Argument String calibrationExpirationDate) {
        return equipmentLogic.CreateEquipment(name, type, calibrationExpirationDate);
    }

    /**
     * Deletes an equipment from the database
     * @param name the name of the equipment to be deleted
     * @return the deleted equipment
     */

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Equipment DeleteEquipment(@Argument String name) {
        return equipmentLogic.deleteEquipment(name);
    }

}