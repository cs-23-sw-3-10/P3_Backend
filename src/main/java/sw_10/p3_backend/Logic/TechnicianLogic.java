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

    /**
     * This method finds a technician depending on its type
     * @param type the type of technician you wish to find
     * @return the technician with the passed type
     */
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

    /**
     * This method checks if the technician already exists, if it does it updates it with the passed values, if not it creates a new technician
     * @param type the type of the technician
     * @param maxWorkHours the maximum work hours this type of technicians should be assigned
     * @param count the number of technicians of this type
     * @return the created/updated technician
     */
    public Technician CreateTechnician(String type, Integer maxWorkHours, Integer count) {
        //Finds the technician in the database if it exists
        Technician technician = technicianRepository.findByType(type);
        try {
            //Checks if the values passed are valid
            if (type == null || maxWorkHours == null || count == null) {
                throw new InputInvalidException("cannot parse null");
            } //check if technician exists
            if (technician != null) {
                //Update values
                technician.setMaxWorkHours(maxWorkHours);
                technician.setCount(count);
                System.out.println("Technician updated");
                technicianRepository.save(technician);
                return technician;
            }
            //Creates a new technician if it does not already exist and then sets its values
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

    /**
     * This method updates a certain technicians work hours by the passed duration
     * @param technician the technician to be updated
     * @param duration how much work hours should increase
     */
    public void updateTechnician(Technician technician, int duration) {
        technician.setWorkHours(technician.getWorkHours() + duration);
        technicianRepository.save(technician);
    }

    /**
     * This method finds a technician by its type
     * @param resourceName equal to the technicians type
     * @return the technician with the passed type
     */
    public Technician findTechnicians(String resourceName) {
        return technicianRepository.findByType(resourceName);
    }

    /**
     * This method deletes a technician, if it is not assigned to any blade tasks
     * @param type the type of technician to be deleted
     * @return the deleted technician
     */
    public Technician deleteTechnician(String type) {
        try {
            //Finds the technician in the database if it exists
            Technician technician = technicianRepository.findByType(type);
            //Checks if the technician exists
            if (technician != null) {
                /*if (technician.getWorkHours() > 0) {
                    throw new InputInvalidException("Technician has " + technician.getWorkHours() + " work hours assigned. Cannot delete technician with work");
                }*/
                //Finds all resourceOrders that has the technician assigned
                List<ResourceOrder> resourceOrders = resourceOrderLogic.findResourceName(technician.getType());
                //Checks if there are any resourceOrders assigned to the technician, since it cannot be deleted if there are
                if (!resourceOrders.isEmpty()) {
                    throw new InputInvalidException("Cannot delete technician with assigned BladeTasks. Currently assigned " + resourceOrders.size() + " blade tasks");

                }
                // TODO ressourceoOrders check efter type
                // Deletes the technician
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

    /**
     * This method finds all technicians
     * @return a list of all technicians
     */
    public List<Technician> findAll(){
        return technicianRepository.findAll();
    }

}

