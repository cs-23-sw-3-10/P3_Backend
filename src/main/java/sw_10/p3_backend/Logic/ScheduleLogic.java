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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     * This method finds both schedules
     * @return a list of both schedules
     */
    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    /**
     * This methode clones and replaces the view schedule with a copy of the edit schedule
     * @return the new view schedule
     * @throws CloneNotSupportedException
     */
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

        //Saves the new view schedule
        scheduleRepository.save(newViewSchedule);

        //Sends the updated schedule to the front end
        bladeTaskLogic.onDatabaseUpdate();
        return newViewSchedule;

    }

    /**
     * This method deletes the schedule with a certain id
     * @param id
     * @return the deleted schedule
     */
    public Schedule deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Schedule with id " + id + " not found"));
        System.out.println("deleteSchedule");
        scheduleRepository.deleteById(Long.valueOf(id));
        return schedule;
    }

    /**
     * This method replaces the edit schedule with a copy of the view schedule
     * @return the new edit schedule
     * @throws CloneNotSupportedException
     */
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

        //TODO SEB SKRIV KOMMENTARER TIL DET HER!!!!

        cloneScheduleAndReplace();

        return newEditSchedule;
    }
}