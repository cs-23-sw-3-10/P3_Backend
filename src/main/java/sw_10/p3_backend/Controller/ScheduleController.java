package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

    @QueryMapping
    public List<Schedule> AllSchedules() throws CloneNotSupportedException {

        scheduleLogic.cloneSchedule();
        return scheduleLogic.findAll();
    }

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
