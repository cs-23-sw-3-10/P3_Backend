package sw_10.p3_backend.Logic;

import graphql.com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.Repository.ConflictRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ConflictLogic {

    private final ConflictRepository conflictRepository;

    private BladeTaskLogic bladeTaskLogic;

    public ConflictLogic(ConflictRepository conflictRepository) {
        this.conflictRepository = conflictRepository;
    }

    public void setBladeTaskLogic(BladeTaskLogic bladeTaskLogic){
        this.bladeTaskLogic = bladeTaskLogic;
    }

    /**
     * This method creates a conflict and finds all related bladetasks
     * @param booking The booking that is in conflict
     * @param bladeTask The blade task that the booking belongs to
     * @return The created conflict
     */
    public Conflict createConflict(Booking booking, BladeTask bladeTask) {

        //Finds all bladetasks that are related to the conflict
        List<BladeTask> relatedBladeTasks = bladeTaskLogic.getRelatedBladeTasksByEquipmentType(booking.getResourceName(), booking.getStartDate(), booking.getEndDate());
        //Makes a hash set of the related bladetasks to ensure no duplicates
        Set<BladeTask> hashedBladeTasks = Sets.newHashSet(relatedBladeTasks);

        Conflict conflict = new Conflict(booking, bladeTask, hashedBladeTasks);

        return conflict;
    }

    /**
     * This method finds all conflicts related to the bookings in the list and deletes them
     * @param bookings The bookings to delete conflicts for
     */
    public void removeConflicts(List<Booking> bookings) {
        //Runs through all bookings and deletes all conflicts related to them
        for (Booking booking : bookings) {
            List<Conflict> conflicts = conflictRepository.findAllByBooking(booking);
            conflictRepository.deleteAll(conflicts);
        }
    }

    /**
     * This method finds the conflict that belong to a certain booking
     * @param bookingId The id of the booking to find the conflict for
     * @param isActive Whether the conflict is in view schedule (true) or in edit schedule (false)
     * @return The conflict that belongs to the booking and is in the correct schedule
     */
    public Conflict findConflictByBookingId(int bookingId, boolean isActive) {
        return conflictRepository.findConflictByBookingId(bookingId, isActive);
    }

    /**
     * This method finds all conflicts
     * @return A list of all conflicts
     */
    public List<Conflict> findAll(){
        return conflictRepository.findAll();
    }
}