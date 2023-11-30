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
            Engineer engineer = engineerRepository.findByName(name);
        try {
            if (name == null || maxWorkHours == null)
                throw new InputInvalidException("cannot parse null");
            if (engineer != null) { //check if engineer exists
                engineer.setMaxWorkHours(maxWorkHours);
                return engineerRepository.save(engineer);
            }
            Engineer newEngineer = new Engineer();
            newEngineer.setName(name);
            newEngineer.setMaxWorkHours(maxWorkHours);
            newEngineer.setWorkHours(0);
            return engineerRepository.save(newEngineer);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
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

    public Engineer deleteEngineer(String name) {
        try {
            Engineer engineer = engineerRepository.findByName(name);
            if (engineer != null) {
                engineerRepository.delete(engineer);
                return engineer;
            } else {
                throw new NotFoundException("Engineer not found with name: " + name);
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting engineer",e);

        }


    }

}







