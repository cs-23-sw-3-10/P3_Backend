package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import sw_10.p3_backend.Logic.BladeTaskLogic;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Model.BladeTaskInput;
import sw_10.p3_backend.exception.InputInvalidException;

import java.util.List;


@Controller
public class BladeTaskController {

    private final BladeTaskLogic bladeTaskLogic;

    BladeTaskController(BladeTaskLogic bladeTaskLogic){
        this.bladeTaskLogic = bladeTaskLogic;
    }

    /**
     * Fetches all blade tasks that overlaps with the passed period and is in the schedule that you want
     * @param startDate start of the overlap period
     * @param endDate end of the overlap period
     * @param isActive chooses which schedule you want to fetch from
     * @return all blade tasks that overlaps with the passed period and is in the chosen schedule
     */
    @QueryMapping
    public List<BladeTask> AllBladeTasksInRange(@Argument String startDate, @Argument String endDate, @Argument boolean isActive){
        return bladeTaskLogic.bladeTasksInRange(startDate, endDate, isActive);
    }


    /**
     * Fetches all blade tasks
     * @return all blade tasks
     */
    @QueryMapping
    public List<BladeTask> AllBladeTasks(){
        return bladeTaskLogic.findAll();
    }

    //TODO have Seb write this
    /**
     * Lets clients subscribe and receive updates when there is a change
     * @return all blade tasks
     */
    @SubscriptionMapping
    public List<BladeTask> bladeTasksAdded(){
        System.out.println("bladeTasksAdded");
        return bladeTaskLogic.findAll();
    }

    /**
     * Gets a blade task by its id
     * @param id
     * @return the blade task with the passed id
     */
    @QueryMapping
    public BladeTask BladeTaskById(@Argument Integer id) {
        try {
            if (id == null) { // Just in case. Should be caught by GraphQL validation.
                throw new InputInvalidException("cannot parse null");
            }
            return bladeTaskLogic.findOne(id);
        } catch (InputInvalidException e) {
            throw new InputInvalidException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Fetches all conflicts for a blade task with a certain id
     * @param id
     * @param isActive determines which schedule you fetch from
     * @return all conflicts for the blade task with the passed id
     */
    @QueryMapping
    public List<Conflict> findConflictsForBladeTask(@Argument Integer id, @Argument Boolean isActive){
        return bladeTaskLogic.findConflictsForBladeTask(id,isActive);
    }

/*    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteBladeTask(@Argument Integer id){
        return bladeTaskLogic.deleteTask(id);
    }*/

    /**
     * Creates a new blade task
     * @param bladeTask contains all the information needed to create a new blade task
     * @return the new blade task
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeTask createBladeTask(@Argument BladeTaskInput bladeTask){
        return bladeTaskLogic.createBladeTask(bladeTask);
    }

    /**
     * This method updates the information of a blade task with a certain id and updates its bookings and conflicts
     * @param updates all the information needed to edit a blade task
     * @param btId the id of the blade task that is edited
     * @return the editted blade task
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public BladeTask updateBTInfo(@Argument BladeTaskInput updates, @Argument Long btId){
        return bladeTaskLogic.updateBTInfo(updates, btId);
    }

    /**
     * This method updates the start date, test rig and duration of a blade task and updates its bookings and conflicts
     * @param id the id of the blade task that is updated
     * @param startDate the new start date
     * @param duration the new duration
     * @param testRig the new test rig
     * @return the updated blade task
     */
    @MutationMapping
    public BladeTask updateStartAndDurationBladeTask(@Argument Long id, @Argument String startDate, @Argument Integer duration, @Argument Integer testRig){
        return bladeTaskLogic.updateStartAndDurationBladeTask(id, startDate, duration, testRig);
    }

    /**
     * Deletes a blade task and all its conflicts and bookings
     * @param id the id of the deleted blade task
     * @return a String with the id of the deleted blade task
     */
    @MutationMapping
    public String deleteBladeTask(@Argument Long id){
        bladeTaskLogic.deleteBladeTask(id);
        return "BladeTask with id: " + id;
    }

    //TODO have Seb write this
    /**
     *
     * @param startDate
     * @param endDate
     * @param isActive
     * @return
     */
    @SubscriptionMapping
    public Flux<List<BladeTask>> AllBladeTasksInRangeSub(@Argument String startDate, @Argument String endDate, @Argument boolean isActive) {
        System.out.println("AllBladeTasksInRangeSub");
        return bladeTaskLogic.bladeTasksInRangeSub(startDate, endDate, isActive);
    }

    //TODO have Seb write this
    /**
     *
     * @param isActive
     * @return
     */
    @SubscriptionMapping
    public Flux<List<BladeTask>> AllBladeTasksPendingSub(@Argument boolean isActive) {
        System.out.println("bladeTasksPendingSub");
        return bladeTaskLogic.bladeTasksPendingSub(isActive);
    }

}
