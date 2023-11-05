package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.IdNotFoundException;
import java.util.List;


@Service
public class ScheduleLogic {
    private final ScheduleRepository scheduleRepository;


    ScheduleLogic(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;

    }

    public Schedule ScheduleById(Integer id){
        try {
            return scheduleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IdNotFoundException("No schedule found with id: " + id));
        }catch (IdNotFoundException e) {
            System.out.println("schedule not found: " + e.getMessage());
            throw e;
        }catch (Exception e) {
        throw new RuntimeException("Error getting schedule",e);
    }
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }



}
