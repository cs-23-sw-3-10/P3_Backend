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


    ScheduleLogic(ScheduleRepository scheduleRepository, BladeTaskLogic bladeTaskLogic) {
        this.scheduleRepository = scheduleRepository;
        this.bladeTaskLogic = bladeTaskLogic;

    }

    /**
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
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot parse null");
        } catch (Exception e) {
            throw new RuntimeException("Error getting Schedule");
        }

    }

    /**
     * This method finds both schedules
     *
     * @return a list of both schedules
     */
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    /**
     * This method deletes the schedule with a certain id
     *
     * @param id
     * @return the deleted schedule
     */
    public Schedule deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Schedule with id " + id + " not found"));
        scheduleRepository.deleteById(Long.valueOf(id));
        return schedule;
    }

    /**
     * This method replaces a schedule based on the isActive boolean passed to the method and returns the
     *
     * @return the updated schedule
     * @throws CloneNotSupportedException
     */
    public Schedule cloneAndReplaceSchedule(boolean isActive) throws CloneNotSupportedException {

        //Find the schedules to clone from and to clone to (view and edit) based on the isActive boolean
        Schedule ScheduleToUpdate = scheduleRepository.findScheduleByIsActive(isActive);
        Schedule ScheduleToClone = scheduleRepository.findScheduleByIsActive(!isActive);

        //deletes the schedule to update
        scheduleRepository.delete(ScheduleToUpdate);

        //Clones the schedule to from the schedule to clone
        Schedule UpdatedSchedule = (Schedule) ScheduleToClone.clone(isActive);

        //Sets the new schedule to active or not active based on the isActive boolean
        UpdatedSchedule.setActive(isActive);

        //save the updated schedule
        scheduleRepository.save(UpdatedSchedule);

        //Sends the updated schedule to the front end
        bladeTaskLogic.onDatabaseUpdate();

        return UpdatedSchedule;
    }
}