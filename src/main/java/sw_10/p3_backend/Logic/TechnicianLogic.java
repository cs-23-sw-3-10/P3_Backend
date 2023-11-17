package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.Optional;

@Service
public class TechnicianLogic {
    private final TechnicianRepository technicianRepository;

    public TechnicianLogic(TechnicianRepository technicianRepository){
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

    public Technician CreateTechnician(String type, Integer maxWorkHours, Integer count) {
        try {
            Technician technician = new Technician();
            technician.setType(type);
            technician.setMaxWorkHours(maxWorkHours);
            technician.setWorkHours(0);
            technician.setCount(count);
            return technicianRepository.save(technician);
        } catch (Exception e) {
            throw new RuntimeException("Error creating technician");
        }
    }
}
