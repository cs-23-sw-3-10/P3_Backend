package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EngineerLogic {
    private final EngineerRepository engineerRepository;
    private final ResourceOrderLogic resourceOrderLogic;

    EngineerLogic(EngineerRepository engineerRepository, ResourceOrderLogic resourceOrderLogic) {
        this.engineerRepository = engineerRepository;
        this.resourceOrderLogic = resourceOrderLogic;
    }

    /**
     * This method finds an engineer by its id
     * @param id the id of the engineer you wish to find
     * @return  the engineer with the given id
     * @throws NotFoundException
     */
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

    /**
     * This method creates a new engineer if it does not already exist, otherwise it updates the existing engineer
     * @param name the name of the engineer
     * @param maxWorkHours the max work hours of the engineer
     * @return the engineer that was created or updated
     */
    public Engineer CreateEngineer(String name, Integer maxWorkHours) {
        //Finds the engineer by name if it exists
        Engineer engineer = engineerRepository.findByName(name);
        try {
            if (name == null || maxWorkHours == null)
                throw new InputInvalidException("cannot parse null");
            //Checks if the engineer exists, if it does, update it, otherwise create a new engineer
            if (engineer != null) {
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

    /**
     * This method finds an engineer by its name
     * @param name the name of the engineer you wish to find
     * @return  the engineer with the given name
     * @throws NotFoundException
     */
    public Engineer findByName(String name) {
        return engineerRepository.findByName(name);
    }

    /**
     * This method updates the work hours of an engineer
     * @param engineer the engineer that is being updated
     * @param workHours the new work hours of the engineer
     */
    public void updateEngineer(Engineer engineer, int workHours) {
        engineer.setWorkHours(workHours);
        engineerRepository.save(engineer);
    }

    /**
     * This method deletes an engineer by its name if it exists and is not assigned to a task
     * @param name the name of the engineer that is being deleted
     * @return the engineer that was deleted
     */
    public Engineer deleteEngineer(String name) {
        try {
            //Finds the engineer by name if it exists
            Engineer engineer = engineerRepository.findByName(name);
            if (engineer != null) {
                List<ResourceOrder> resourceOrders = resourceOrderLogic.findResourceName(name);
                if (!resourceOrders.isEmpty())
                    throw new InputInvalidException("Cannot delete engineer that is assigned to a task. Currently assigned " + resourceOrders.size() + " blade tasks");
                engineerRepository.delete(engineer);
                return engineer;
            } else {
                throw new NotFoundException("Engineer not found with name: " + name);
            }
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting engineer",e);
        }
    }

    /**
     * This method finds all engineers
     * @return the list of all engineers
     */
    public List<Engineer> findAll(){
        return engineerRepository.findAll();
    }

}







