package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.List;


@Service
public class ScheduleLogic {
    private final ScheduleRepository scheduleRepository;


    ScheduleLogic(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;

    }

    public Schedule ScheduleById(Integer id) {
        return scheduleRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("No schedule found with id: " + id));
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

}
