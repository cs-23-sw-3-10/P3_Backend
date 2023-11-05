package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.ScheduleLogic;
import sw_10.p3_backend.Model.Schedule;
import java.util.List;


@Controller
public class ScheduleController {

    private final ScheduleLogic scheduleLogic;

    ScheduleController(ScheduleLogic scheduleLogic){
        this.scheduleLogic = scheduleLogic;
    }

    @QueryMapping
    public List<Schedule> AllSchedules(){
        return scheduleLogic.findAll();
    }

    @QueryMapping
    public Schedule ScheduleById(@Argument Integer id){
        return scheduleLogic.ScheduleById(id);
    }
}
