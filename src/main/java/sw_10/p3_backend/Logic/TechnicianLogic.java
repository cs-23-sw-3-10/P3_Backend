package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.Optional;

@Service
public class TechnicianLogic {
    private final TechnicianRepository technicianRepository;

    public TechnicianLogic(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
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
                return technicianRepository.save(technician);
            }
            technician = new Technician();
            technician.setType(type);
            technician.setMaxWorkHours(maxWorkHours);
            technician.setWorkHours(0);
            technician.setCount(count);
            return technicianRepository.save(technician);
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
}

/*
        try {
            if (type == null || maxWorkHours == null || count == null) {
                throw new InputInvalidException("cannot parse null");
            }
            if (technician != null) // technician exists. Then update
            {
                technician.setMaxWorkHours(maxWorkHours);
                technician.setCount(count);
                technician.setWorkHours(0);
                return technicianRepository.save(technician);
            }
            technician.setType(type);
            technician.setMaxWorkHours(maxWorkHours);
            technician.setWorkHours(0);
            technician.setCount(count);
            return technicianRepository.save(technician);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating technician");
        }
    }
}
 */

