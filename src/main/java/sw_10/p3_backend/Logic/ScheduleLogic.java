package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.Repository.ScheduleRepository;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.List;
import java.util.Optional;


@Service
public class ScheduleLogic {
    private final ScheduleRepository scheduleRepository;


    ScheduleLogic(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;

    }

    public Schedule ScheduleById(Integer id) {
        try {
             Optional<Schedule> schedule = scheduleRepository.findById(Long.valueOf(id));
            if (schedule.isEmpty()) {
                throw new NotFoundException("No schedule found with id: " + id);
            }
            return schedule.get();
        } catch (NotFoundException  e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Cannot parse null");
        } catch (Exception e) {
            throw new RuntimeException("Error getting Schedule");
        }

    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Schedule cloneScheduleAndReplace() throws CloneNotSupportedException {

        System.out.println("cloneSchedule");
        Schedule schedule = scheduleRepository.findScheduleByIsActive(true);
        Schedule newSchedule = (Schedule) schedule.clone();
        newSchedule.setActive(true);
        System.out.println(newSchedule.getId());
        scheduleRepository.save(newSchedule);
        scheduleRepository.delete(schedule);

        return schedule;

    }

    public Schedule deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Schedule with id " + id + " not found"));
        System.out.println("deleteSchedule");
        scheduleRepository.deleteById(Long.valueOf(id));
        return schedule;
    }
}