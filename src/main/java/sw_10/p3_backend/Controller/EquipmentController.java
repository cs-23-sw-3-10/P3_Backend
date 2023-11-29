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


    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Equipment CreateEquipment(@Argument String name, @Argument String type, @Argument String calibrationExpirationDate) {
        return equipmentLogic.CreateEquipment(name, type, calibrationExpirationDate);
    }
}