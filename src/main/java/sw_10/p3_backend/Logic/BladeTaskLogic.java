package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.Repository.ConflictRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;


import java.util.function.Supplier;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BladeTaskLogic {
    private final BladeTaskRepository bladeTaskRepository;
    private final BladeProjectRepository bladeProjectRepository;

    private final BookingLogic bookingLogic;
    private final ResourceOrderLogic resourceOrderLogic;
    private final BladeProjectLogic bladeProjectLogic;

    private final ConflictLogic conflictLogic;


    /**
     * Defines a processor that acts as a sink for multiple publishers and a source for one or more subscribers.
     * It is configured as a 'multicast' sink, meaning it can handle multiple publishers and will dispatch
     * the emitted items to all subscribers. The 'onBackpressureBuffer' handles backpressure,
     * if subscribers cannot keep up with the rate of items being emitted, the items are buffered until
     * they can be processed. This buffer ensures that no data is lost if the downstream subscribers are slower
     * than the producers, with our amount of subscriber buffer overload should not be a problem.
     */
    private final Sinks.Many<Object> processor = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    public BladeTaskLogic(BladeTaskRepository bladeTaskRepository, BladeProjectRepository bladeProjectRepository, ConflictRepository conflictRepository
            , BookingLogic bookingLogic, ResourceOrderLogic resourceOrderLogic, BladeProjectLogic bladeProjectLogic, ConflictLogic conflictLogic) {
        this.bladeTaskRepository = bladeTaskRepository;
        this.bladeProjectRepository = bladeProjectRepository;
        this.bookingLogic = bookingLogic;
        this.resourceOrderLogic = resourceOrderLogic;
        this.bladeProjectLogic = bladeProjectLogic;
        this.conflictLogic=conflictLogic;
    }


    public String deleteTask(Integer id) {
        try {
            bladeTaskRepository.deleteById(id.longValue());
            return "BT deleted";
        } catch (Exception e) {
            return "Error deleting BT" + e;
        }
    }

    //TODO: add validation of equipmentAssignmentStatus of resource orders "Must be consecutive" or add logic to handle this e.g. multiple bookings pr resource order
    public BladeTask createBladeTask(BladeTaskInput input) {
        System.out.println(input.startDate());
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        validateBladeTaskInput(input);

        // Find the blade project in the database
        BladeProject bladeProject = getBladeProject(Long.valueOf(input.bladeProjectId()));

        LocalDate startDate = input.startDate();
        Integer totalDuration = input.duration() + input.attachPeriod() + input.detachPeriod();
        // Calculate the end date of the blade task
        LocalDate endDate = calculateEndDate(startDate, totalDuration);

        //Set testrig to 0 if none is provided
        int noTestRigAssignedValue = 0;
        int testRigValue = Optional.ofNullable(input.testRig()).orElse(noTestRigAssignedValue);
        System.out.println(testRigValue);

        // Create a new BladeTask instance
        BladeTask newBladeTask = new BladeTask(
                input.startDate(),
                endDate,
                totalDuration,
                input.testType(),
                input.attachPeriod(),
                input.detachPeriod(),
                input.taskName(),
                testRigValue,
                bladeProject
        );


        // Create resource orders for the blade task (if any)
        List<ResourceOrder> resourceOrders = handleResourceOrders(input, newBladeTask);

        // Save the new BladeTask in the database
        bladeTaskRepository.save(newBladeTask);

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided

        if (testRigValue != 0 && resourceOrders != null) {
            bookingLogic.createBookings(resourceOrders, newBladeTask);
        }
        bladeProjectLogic.updateBladeProject(newBladeTask.getBladeProjectId());
        // Return the new BladeTask

        onDatabaseUpdate();
        return newBladeTask;
    }

    private BladeProject getBladeProject(Long bladeProjectId) {
        return bladeProjectRepository.findById(bladeProjectId)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + bladeProjectId));
    }

    private List<ResourceOrder> handleResourceOrders(BladeTaskInput input, BladeTask newBladeTask) {
        if (input.resourceOrders() != null) {
            return resourceOrderLogic.createResourceOrders(input.resourceOrders(), newBladeTask);
        }
        return null;
    }

    public List<BladeTask> findAll() {
        return bladeTaskRepository.findAll();
    }

    public BladeTask findOne(Integer id) throws NotFoundException {
        try {
            Optional<BladeTask> bladeTask = bladeTaskRepository.findById(Long.valueOf(id));
            if (bladeTask.isEmpty())
                throw new NotFoundException("BladeTask not found with id: " + id);
            return bladeTask.get();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error getting BladeTask", e);
        }
    }

    private void validateBladeTaskInput(BladeTaskInput input) {

        if ((input.startDate() == null && input.testRig() != null) || (input.startDate() != null && input.testRig() == null)) {
            throw new InputInvalidException("Both startDate and testRig must be provided together");
        }
        if (input.testRig() != null && input.testRig() < 0) {
            throw new InputInvalidException("testRig cannot be negative");
        }
        if (input.bladeProjectId() == null) {
            throw new InputInvalidException("bladeProjectId is mandatory");
        }
        if (input.duration() == null) {
            throw new InputInvalidException("duration is mandatory");
        }
        if (input.testType() == null) {
            throw new InputInvalidException("testType is mandatory");
        }
        if (input.attachPeriod() == null) {
            throw new InputInvalidException("attachPeriod is mandatory");
        }
        if (input.detachPeriod() == null) {
            throw new InputInvalidException("detachPeriod is mandatory");
        }
        if (input.taskName() == null) {
            throw new InputInvalidException("taskName is mandatory");
        }
        if (input.startDate() != null && LocalDate.now().isAfter(input.startDate())) {
            throw new InputInvalidException("startDate cannot be in the past");
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, Integer duration) {
        if (startDate != null && duration != null) {
            return startDate.plusDays(duration);
        }
        return null;
    }

    public BladeTask updateStartAndDurationBladeTask(Long id, String startDate, Integer duration, Integer testRig) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        LocalDate startDateParsed;
        if (startDate.equals("undefined")) {
            startDateParsed = null;
        } else {
            //parsed startDate to LocalDate
            startDateParsed = LocalDate.parse(startDate);
        }

        bladeTaskToUpdate.setStartDate(startDateParsed);
        bladeTaskToUpdate.setDuration(duration);
        // Calculate the end date of the blade task
        bladeTaskToUpdate.setEndDate(calculateEndDate(startDateParsed, duration));
        bladeTaskToUpdate.setTestRig(testRig);

        //Set testrig to 0 if none is provided
        int noTestRigAssignedValue = 0;
        int testRigValue = Optional.of(bladeTaskToUpdate.getTestRig()).orElse(noTestRigAssignedValue);
        System.out.println(testRigValue);

        //Remove old bookings
        bookingLogic.removeBookings(bladeTaskToUpdate);

        // Save the new BladeTask in the database

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided
        if (testRigValue != 0 && bladeTaskToUpdate.getResourceOrders() != null) {
            System.out.println("Creating bookings");
            bookingLogic.createBookings(bladeTaskToUpdate.getResourceOrders(), bladeTaskToUpdate);
        }


        bladeProjectLogic.updateBladeProject(bladeTaskToUpdate.getBladeProjectId());
        bladeTaskRepository.save(bladeTaskToUpdate);
        System.out.println(testRigValue);

        onDatabaseUpdate();
        // Return the new BladeTask
        return bladeTaskToUpdate;
    }

    public List<BladeTask> bladeTasksInRange(String startDate, String endDate, boolean isActive) {
        return bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive);
    }


    public BladeTask updateBTInfo(BladeTaskInput updates, Long btId) {
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(btId)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + btId));

        LocalDate endDate = calculateEndDate(updates.startDate(), updates.duration());

        System.out.println("startDate: " + updates.startDate());

        System.out.println("endDate: " + endDate);

        List<BladeTask> bladeTasksInRange = bladeTaskRepository.bladeTasksInRange(updates.startDate(), endDate, false);

        System.out.println("bladeTasksInRange: " + bladeTasksInRange);

        if (bladeTaskToUpdate.getStartDate() != updates.startDate()
                || bladeTaskToUpdate.getEndDate() != endDate)
        {
            for (BladeTask bladeTask : bladeTasksInRange) {
                if (bladeTask.getTestRig() == updates.testRig() && bladeTask.getId() != btId) {
                    System.out.println("her er id " + bladeTask.getTestRig() + "og taskName " + bladeTask.getTaskName());
                    throw new InputInvalidException("BladeTask with testRig " + updates.testRig() + " already exists in the given time period");
                }
            }
            bladeTaskToUpdate.setStartDate(updates.startDate());
            bladeTaskToUpdate.setEndDate(endDate);
        }

        if (bladeTaskToUpdate.getDuration() != updates.duration()){
            bladeTaskToUpdate.setDuration(updates.duration());
        }
        if (bladeTaskToUpdate.getTestRig() != updates.testRig()){
            bladeTaskToUpdate.setTestRig(updates.testRig());
        }
        if (bladeTaskToUpdate.getAttachPeriod() != updates.attachPeriod()){
            bladeTaskToUpdate.setAttachPeriod(updates.attachPeriod());
        }
        if (bladeTaskToUpdate.getDetachPeriod() != updates.detachPeriod()){
            bladeTaskToUpdate.setDetachPeriod(updates.detachPeriod());
        }
        if (bladeTaskToUpdate.getTaskName().equals(updates.taskName())){
            bladeTaskToUpdate.setTaskName(updates.taskName());
        }
        if (bladeTaskToUpdate.getTestType().equals(updates.testType())){
            bladeTaskToUpdate.setTestType(updates.testType());
        }
        // der skal tilføjes en metode til at opdatere resourceOrders men der er problemer med dem fordi de skal opdateres eller noget
        /*if (bladeTaskToUpdate.getResourceOrders() != updates.resourceOrders()){
            bladeTaskToUpdate.setResourceOrders(updates.resourceOrders());
        }*/
        bladeProjectLogic.updateBladeProject(bladeTaskToUpdate.getBladeProjectId());
        bladeTaskRepository.save(bladeTaskToUpdate);

        return bladeTaskToUpdate;
    }

    public List<Conflict> findConflictsForBladeTask(Integer id, Boolean isActive) {
        System.out.println("Vi finder konflikter");

        BladeTask bladeTask=this.findOne(id);

        List<Booking> bookings=bladeTask.getBookings();

        List<Conflict> conflicts=new ArrayList<>();

        for(Booking booking: bookings){
            Conflict conflict=conflictLogic.findConflictByBookingId(booking.getId(),isActive);

            if(conflict!=null){
                conflicts.add(conflict);
            }
        }
        return conflicts;
    }

    public Flux<List<BladeTask>> bladeTasksInRangeSub(String startDate, String endDate, boolean isActive) {
        // Create a Flux stream to emit the list of blade tasks in a given range when to subscribers whenever an update occurs.
        return createFlux(() -> bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive));
        //The call to the repository is the supplier that provides the actual data (list of BladeTasks) to be emitted by the Flux.
    }

    public Flux<List<BladeTask>> bladeTasksPendingSub() {
        // Create a Flux stream to emit the list of blade tasks pending when to subscribers whenever an update occurs.
        return createFlux(bladeTaskRepository::bladeTasksPending);
    }

    private Flux<List<BladeTask>> createFlux(Supplier<List<BladeTask>> supplier) {
        // Generalization of the creation of the Flux stream based on a supplier.
        // The supplier provides the actual data (list of BladeTasks) to be emitted by the Flux.

        return Flux.defer(() -> Flux.just(supplier.get()))
                // Flux.defer ensures that each subscriber receives its own version of the Flux sequence.
                // It delays the creation of the Flux until a subscriber subscribes. Flux.just takes the data provided
                // by the database calls and wraps them in a Flux.

                .concatWith(Flux.defer(() -> processor.asFlux() // The concatWith operation appends another Flux to the initial one.
                        .publishOn(Schedulers.boundedElastic()) // Make sure that the updates are handled on a separate thread
                        .map(ignored -> supplier.get()))); // The map operation ignores the signal from the processor and runs the query again to get the updated data directly from the database


    }

    public void onDatabaseUpdate() {
        //Updates the processor with a new object to trigger a signal emission to subscribers that will run the query again
        processor.tryEmitNext(new Object());
    }


    public List<BladeTask> bladeTasksPending() {
        return bladeTaskRepository.bladeTasksPending();

    }
}


