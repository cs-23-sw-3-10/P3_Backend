package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;
import sw_10.p3_backend.Logic.ResourceOrderLogic;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnicianLogic {
    private final TechnicianRepository technicianRepository;
    private final ResourceOrderLogic resourceOrderLogic;

    public TechnicianLogic(TechnicianRepository technicianRepository, ResourceOrderLogic resourceOrderLogic) {
        this.technicianRepository = technicianRepository;
        this.resourceOrderLogic = resourceOrderLogic;
    }

    public Technician TechnicianByType(String type) {
        try {
            Technician technician = technicianRepository.findByType(type);
            if (type == null) {
                throw new IllegalArgumentException("cannot parse null");
            }
            if (technician == null) {
                throw new NotFoundException("Technician not found with type: " + type);
            }
            return technician;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw e;

        }
    }

    // If technician exists, update. Else create new technician with param values
    public Technician CreateTechnician(String type, Integer maxWorkHours, Integer count) {
        Technician technician = technicianRepository.findByType(type);
        try {
            if (type == null || maxWorkHours == null || count == null) {
                throw new InputInvalidException("cannot parse null");
            } //check if technician exists
            if (technician != null) {
                technician.setMaxWorkHours(maxWorkHours);
                technician.setCount(count);
                System.out.println("Technician updated");
                technicianRepository.save(technician);
                return technician;
            }
            technician = new Technician();
            technician.setType(type);
            technician.setMaxWorkHours(maxWorkHours);
            technician.setWorkHours(0);
            technician.setCount(count);

            technicianRepository.save(technician);
            return technician;
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating technician");
        }
    }

    public void updateTechnician(Technician technician, int duration) {
        technician.setWorkHours(technician.getWorkHours() + duration);
        technicianRepository.save(technician);
    }
    public Technician findTechnicians(String resourceName) {
        return technicianRepository.findByType(resourceName);
    }

    public Technician deleteTechnician(String type) {
        try {
            Technician technician = technicianRepository.findByType(type);
            if (technician != null) {
                /*if (technician.getWorkHours() > 0) {
                    throw new InputInvalidException("Technician has " + technician.getWorkHours() + " work hours assigned. Cannot delete technician with work");
                }*/
                List<ResourceOrder> resourceOrders = resourceOrderLogic.findResourceName(technician.getType());
                if (!resourceOrders.isEmpty()) {
                    throw new InputInvalidException("Cannot delete technician with assigned BladeTasks. Currently assigned " + resourceOrders.size() + " blade tasks");

                }
                // TODO ressourceoOrders check efter typex
                // kald fra TechnicianLogic til ressourceOrderLogic som kalder resourceorderrepository
                technicianRepository.delete(technician);
                return technician;
            } else {
                throw new NotFoundException("Technician not found with type: " + type);
            }
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting technician");
        }
    }

}

