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
    private final BladeTaskLogic bladeTaskLogic;


    ScheduleLogic(ScheduleRepository scheduleRepository , BladeTaskLogic bladeTaskLogic){
        this.scheduleRepository = scheduleRepository;
        this.bladeTaskLogic = bladeTaskLogic;

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

        //Find editable schedule (active)
        Schedule CurrentEditschedule = scheduleRepository.findScheduleByIsActive(false);
        Schedule CurrentViewSchedule = scheduleRepository.findScheduleByIsActive(true);



        //clone active schedule
        Schedule newViewSchedule = (Schedule) CurrentEditschedule.clone(true);

        //set the new schedule to viewable (not active)
        newViewSchedule.setActive(true);

        //TODO: Not secure. If something goes wrong is the old schedule lost and the new one is not saved.
        //save the new schedule (will be the new view only schedule) and delete the old one.
        System.out.println(CurrentViewSchedule);
        if(CurrentViewSchedule != null) {
            scheduleRepository.delete(CurrentViewSchedule);
            System.out.println("Deleted");
        }

        scheduleRepository.save(newViewSchedule);

        bladeTaskLogic.onDatabaseUpdate();
        return newViewSchedule;

    }

    public Schedule deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Schedule with id " + id + " not found"));
        System.out.println("deleteSchedule");
        scheduleRepository.deleteById(Long.valueOf(id));
        return schedule;
    }

    public Schedule discardEditChanges()  throws CloneNotSupportedException {

        //Find editable schedule (active)
        Schedule CurrentEditschedule = scheduleRepository.findScheduleByIsActive(false);
        Schedule CurrentViewSchedule = scheduleRepository.findScheduleByIsActive(true);

        //delete editable schedule
        scheduleRepository.delete(CurrentEditschedule);

        //set the viewable schedule to editable
        Schedule newEditSchedule = (Schedule) CurrentViewSchedule.clone(false);

        newEditSchedule.setActive(false);

        //save the new schedule (will be the new view only schedule) and delete the old one.
        scheduleRepository.save(newEditSchedule);

        cloneScheduleAndReplace();



        return newEditSchedule;
    }
}