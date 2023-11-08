package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Engineer;
import sw_10.p3_backend.Repository.EngineerRepository;
import sw_10.p3_backend.exception.IdNotFoundException;

@Service
public class EngineerLogic {
    private final EngineerRepository engineerRepository;

    EngineerLogic(EngineerRepository engineerRepository){
        this.engineerRepository = engineerRepository;
    }

    public Engineer EngineerById(Integer id){
        try {
            return engineerRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IdNotFoundException("No engineer found with id: " + id));
        } catch (IdNotFoundException e) {
            System.out.println("EngineerById: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error getting engineer",e);
        }
    }
}
