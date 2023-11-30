package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Repository.ConflictRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ConflictLogic {

    private final ConflictRepository conflictRepository;

    public ConflictLogic(ConflictRepository conflictRepository) {
        this.conflictRepository = conflictRepository;
    }

    public List<Conflict> allConflicts() {
        return conflictRepository.findAll();
    }




    public void createConflict(Booking booking, BladeTask bladeTask) {
        //TODO: Add associated bladeTask to conflict
        Conflict conflict = new Conflict(booking, bladeTask);

        conflict.setBooking(booking);

        conflictRepository.save(conflict);
    }

    //TODO: Write log to update conflicts when bookings delete or changed (currently only deletes conflicts when associated booking is deleted)
    public void removeConflicts(List<Booking> bookings) {
        for (Booking booking : bookings) {
            List<Conflict> conflicts = conflictRepository.findAllByBooking(booking);
            conflictRepository.deleteAll(conflicts);
        }
    }


    public Conflict findConflictByBookingId(int id, Boolean isActive) {

        return conflictRepository.findConflictsForBladeTask(id,isActive);

    }
}