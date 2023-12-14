package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.ScheduleLogic;
import sw_10.p3_backend.Model.Schedule;
import sw_10.p3_backend.exception.InputInvalidException;

import java.util.List;


@Controller
public class ScheduleController {

    private final ScheduleLogic scheduleLogic;

    ScheduleController(ScheduleLogic scheduleLogic){
        this.scheduleLogic = scheduleLogic;
    }


    /**
     * This method copies the entire edit schedule and uses it to replace the view schedule
     * @return the new view schedule
     * @throws CloneNotSupportedException
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Schedule cloneScheduleAndReplace() throws CloneNotSupportedException {
        return scheduleLogic.cloneAndReplaceSchedule(true);
    }

    /**
     * This method copies the entire view schedule and uses it to replace the edit schedule
     * @return the new edit schedule
     * @throws CloneNotSupportedException
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Schedule discardEditChanges() throws CloneNotSupportedException {
        return scheduleLogic.cloneAndReplaceSchedule(false);
    }

    /**
     * This method fetches both schedules
     * @return both schedules
     */
    @QueryMapping
    public List<Schedule> AllSchedules() {
        return scheduleLogic.findAll();
    }

    /**
     * This method deletes a schedule and all its linked objects
     * @param id
     * @return the deleted schedule
     */
    @QueryMapping
    public Schedule DeleteSchedule(@Argument Integer id){
        return scheduleLogic.deleteSchedule(id);
    }

    /**
     * This method fetches the schedule with a certain id
     * @param id
     * @return the schedule with the passed id
     */
    @QueryMapping
    public Schedule ScheduleById(@Argument Integer id){
        try {
            if (id == null) {
                throw new InputInvalidException("cannot parse null");
            }
            return scheduleLogic.ScheduleById(id);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }
}
