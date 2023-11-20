package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.Optional;

@Service
public class EngineerLogic {
    private final EngineerRepository engineerRepository;

    EngineerLogic(EngineerRepository engineerRepository){
        this.engineerRepository = engineerRepository;
    }

    public Engineer EngineerById(Integer id) throws NotFoundException {
        try {
            Optional<Engineer> engineer = engineerRepository.findById(Long.valueOf(id));
            if (engineer.isEmpty()) {
                throw new NotFoundException("Engineer not found with id: " + id);
            }
            return engineer.get();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot parse id: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting engineer",e);
        }
    }

    public Engineer CreateEngineer(String name, Integer maxWorkHours) {
        if (engineerRepository.findByName(name) != null)
            throw new InputInvalidException("Engineer with name: " + name + " already exists");
        try {
            Engineer engineer = new Engineer();
            engineer.setName(name);
            engineer.setMaxWorkHours(maxWorkHours);
            engineer.setWorkHours(0);
            return engineerRepository.save(engineer);
        } catch (Exception e) {
            throw new RuntimeException("Error creating engineer",e);
        }
    }

    public Engineer findByName(String name) {
        return engineerRepository.findByName(name);
    }

    public void updateEngineer(Engineer engineer, int workHours) {
        engineer.setWorkHours(workHours);
        engineerRepository.save(engineer);
    }
}
