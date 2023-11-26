package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeTask;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.Conflict;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.Repository.ConflictRepository;
import sw_10.p3_backend.Repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictLogic {

    private final ConflictRepository conflictRepository;

    private final BladeTaskLogic bladeTaskLogic;

    public ConflictLogic(ConflictRepository conflictRepository, BladeTaskLogic bladeTaskLogic) {

        this.conflictRepository = conflictRepository;
        this.bladeTaskLogic = bladeTaskLogic;
    }

    public List<Conflict> allConflicts() {
        return conflictRepository.findAll();
    }


    public void createConflict(Booking booking, BladeTask bladeTask, ResourceOrder resourceOrder) {
        //TODO: Add associated bladeTask to conflict
        List<BladeTask> relatedBladeTasks = bladeTaskLogic.getRelatedBladeTasksByEquipmentType(resourceOrder, booking.getStartDate(), booking.getEndDate());
        Conflict conflict = new Conflict(booking, bladeTask, relatedBladeTasks);
        conflictRepository.save(conflict);
    }

    //TODO: Write log to update conflicts when bookings delete or changed (currently only deletes conflicts when associated booking is deleted)
    public void removeConflicts(List<Booking> bookings) {
        for (Booking booking : bookings) {
            List<Conflict> conflicts = conflictRepository.findAllByBooking(booking);
            conflictRepository.deleteAll(conflicts);
        }
    }
}
