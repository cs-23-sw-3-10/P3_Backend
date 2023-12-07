package sw_10.p3_backend.Logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
import sw_10.p3_backend.Repository.ConflictRepository;
import sw_10.p3_backend.Repository.BookingRepository;
import sw_10.p3_backend.exception.InputInvalidException;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.*;

@Service
public class BladeTaskLogic {
    private final BladeTaskRepository bladeTaskRepository;
    private final BladeProjectRepository bladeProjectRepository;
    private final BookingLogic bookingLogic;
    private final ResourceOrderLogic resourceOrderLogic;
    private final BladeProjectLogic bladeProjectLogic;
    private final BookingRepository bookingRepository;

    @Autowired
    private ConflictLogic conflictLogic;

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
    public BladeTaskLogic(BladeTaskRepository bladeTaskRepository, BladeProjectRepository bladeProjectRepository,
                          BookingLogic bookingLogic, ResourceOrderLogic resourceOrderLogic, BladeProjectLogic bladeProjectLogic, ConflictLogic conflictLogic, BookingRepository bookingRepository) {
        this.bladeTaskRepository = bladeTaskRepository;
        this.bladeProjectRepository = bladeProjectRepository;
        this.bookingLogic = bookingLogic;
        this.resourceOrderLogic = resourceOrderLogic;
        this.bookingRepository = bookingRepository;
        this.bladeProjectLogic = bladeProjectLogic;
        this.conflictLogic = conflictLogic;
    }

    @PostConstruct
    public void init() {
        conflictLogic.setBladeTaskLogic(this);
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
        if (checkForBTOverlap(input.startDate(),
                calculateEndDate(input.startDate(), input.duration()),
                newBladeTask)){
            throw new InputInvalidException("BladeTask with testRig " + input.testRig() + " already exists in the given time period");
        }
        
        // Create resource orders for the blade task (if any)
        List<ResourceOrder> resourceOrders = handleResourceOrders(input, newBladeTask);

        // Save the new BladeTask in the database
        bladeTaskRepository.save(newBladeTask);

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided

        if (testRigValue != 0 && resourceOrders != null) {
            bookingLogic.createBookings(resourceOrders, newBladeTask);
        }
        bladeProjectLogic.updateStartAndEndDate(newBladeTask.getBladeProject());
        bookingLogic.recalculateConflicts(newBladeTask);

        onDatabaseUpdate();
        return newBladeTask;
    }

    private BladeProject getBladeProject(Long bladeProjectId) {
        return bladeProjectRepository.findById(bladeProjectId)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + bladeProjectId));
    }

    private List<ResourceOrder> handleResourceOrders(BladeTaskInput input, BladeTask newBladeTask) {
        if (input.resourceOrders() != null) {
            return resourceOrderLogic.createResourceOrdersBladeTask(input.resourceOrders(), newBladeTask);
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
            return startDate.plusDays(duration-1);
        }
        return null;
    }

    public BladeTask updateStartAndDurationBladeTask(Long id, String startDate, Integer duration, Integer testRig) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        //Remove old bookings
        bookingLogic.removeBookings(bladeTaskToUpdate);
        System.out.println("Bookings deleted");

        //Finding all the related conflicts
        Set<Conflict> relatedConflicts = bladeTaskToUpdate.getRelatedConflicts();
        Set<BladeTask> relatedBladeTasks = new HashSet<>();
        System.out.println("Lists created");

        //Removes the old relations on the bladetask that is being updated. This makes it possible to save the bladetask later
        bookingLogic.resetRelatedConflicts(bladeTaskToUpdate);
        System.out.println("Related conflicts reset");

        //run through all the related conflicts to find the related bladetasks
        for (Conflict relatedConflict : relatedConflicts) {
            Booking tempBooking = relatedConflict.fetchBooking();
            relatedBladeTasks.add(tempBooking.fetchBladeTask());
        }

        //Deletes and recreates all the bookings on the related bladetasks
        for (BladeTask relatedBladeTask : relatedBladeTasks) {
            BladeTask tempBladeTask = bookingLogic.deleteAndRecreateBookings(relatedBladeTask);
            bladeTaskRepository.save(tempBladeTask);
        }

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
        System.out.println("Number of resource orders: " + bladeTaskToUpdate.getResourceOrders().size());


        System.out.println("Just before saving bladetask");
        System.out.println(bladeTaskToUpdate);

        // Save the new BladeTask in the database. This creates problems, if you do not reset the relatedConflicts after deleting them
        bladeTaskRepository.save(bladeTaskToUpdate);
        System.out.println("BT saved");

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided
        if (testRigValue != 0 && bladeTaskToUpdate.getResourceOrders() != null) {
            System.out.println("Creating bookings");
            bookingLogic.createBookings(bladeTaskToUpdate.getResourceOrders(), bladeTaskToUpdate);
        }

        //Checks if the start date and end date of the bladeproject should change and then saves the bladetask
        bladeProjectLogic.updateStartAndEndDate(bladeTaskToUpdate.getBladeProject());
        bladeTaskRepository.save(bladeTaskToUpdate);
        System.out.println(testRigValue);

        //Ensures that older conflicts are updated with the related bladetasks
        bookingLogic.recalculateConflicts(bladeTaskToUpdate);

        //Sends the updates to the clients
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

        int totalDuration = updates.duration() + updates.attachPeriod() + updates.detachPeriod();

        LocalDate endDate = calculateEndDate(updates.startDate(), totalDuration);

        List<BladeTask> bladeTasksInRange = bladeTaskRepository.bladeTasksInRange(updates.startDate(), endDate, false);

        //Checks for overlap with other bladetasks
        if (bladeTaskToUpdate.getStartDate() != updates.startDate()
                || bladeTaskToUpdate.getEndDate() != endDate) {
            if (checkForBTOverlap(updates.startDate(), endDate, bladeTaskToUpdate)){
                throw new InputInvalidException("BladeTask with testRig " + updates.testRig() + " already exists in the given time period");
            }
            bladeTaskToUpdate.setStartDate(updates.startDate());
            bladeTaskToUpdate.setEndDate(endDate);
        }

        //Sets the values of the bladetask to the incoming values
        bladeTaskToUpdate.setDuration(totalDuration);
        bladeTaskToUpdate.setTestRig(updates.testRig());
        bladeTaskToUpdate.setAttachPeriod(updates.attachPeriod());
        bladeTaskToUpdate.setDetachPeriod(updates.detachPeriod());
        bladeTaskToUpdate.setTaskName(updates.taskName());
        bladeTaskToUpdate.setTestType(updates.testType());

        resourceOrderLogic.removeResourceOrders(bladeTaskToUpdate);
        resourceOrderLogic.createResourceOrdersBladeTask(updates.resourceOrders(), bladeTaskToUpdate);

        bladeTaskRepository.save(bladeTaskToUpdate);
        bladeProjectLogic.updateStartAndEndDate(bladeTaskToUpdate.getBladeProject());

        bladeTaskToUpdate = updateStartAndDurationBladeTask((long) bladeTaskToUpdate.getId(), bladeTaskToUpdate.getStartDate().toString(),bladeTaskToUpdate.getDuration(), bladeTaskToUpdate.getTestRig() );

        return bladeTaskToUpdate;
    }

    public List<Conflict> findConflictsForBladeTask(int id, boolean isActive) throws NotFoundException {

        BladeTask bladeTask = this.findOne(id);

        List<Booking> bookings = bladeTask.getBookings();

        List<Conflict> conflicts = new ArrayList<>();

        for (Booking booking : bookings) {
            Conflict conflict = conflictLogic.findConflictByBookingId(booking.getId(), isActive);

            if (conflict != null) {
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

        return Flux.<List<BladeTask>>create(sink -> {
                    // Emit the current state immediately to new subscribers
                    sink.next(supplier.get());

                    // Handle updates from the processor
                    processor.asFlux()
                            .publishOn(Schedulers.boundedElastic())
                            .subscribe(
                                    ignored -> sink.next(supplier.get()),
                                    sink::error,    // Handle errors by propagating them
                                    () -> {
                                    }        // Avoid completing the sink
                            );
                })
                .onErrorContinue((throwable, o) -> {
                    // Log or handle the error
                })
                .share(); // Share the Flux to keep it active
    }

    public void onDatabaseUpdate() {
        //Updates the processor with a new object to trigger a signal emission to subscribers that will run the query again
        processor.tryEmitNext(new Object());
    }


    public List<BladeTask> bladeTasksPending() {
        return bladeTaskRepository.bladeTasksPending();

    }

    //TODO: Find a better way to do this?
    public List<BladeTask> getRelatedBladeTasksByEquipmentType(String equipmentName, LocalDate startDate, LocalDate endDate) {
        System.out.println("Getting relevant bookings");
        List<Booking> bookings = bookingRepository.findBookedEquipmentByTypeAndPeriod(equipmentName, startDate, endDate); //Implement

        //System.out.println("Bookings:");
        //System.out.println(bookings);

        List<BladeTask> bladeTasks = new ArrayList<>();
        for (Booking booking : bookings) {
            //System.out.println(booking);
            BladeTask tempBladeTask = booking.fetchBladeTask();
            //System.out.println(tempBladeTask);
            bladeTasks.add(tempBladeTask);
        }
        System.out.println("BladeTasks:");
        System.out.println(bladeTasks);

        return bladeTasks;
    }


    private boolean checkForBTOverlap(LocalDate startDate, LocalDate endDate, BladeTask checkBladeTask) {

        List <BladeTask> bladeTasksInRange = bladeTaskRepository.bladeTasksInRange(startDate, endDate, false);

        for (BladeTask bladeTask : bladeTasksInRange) {
            LocalDate btStartDate = bladeTask.getStartDate();
            LocalDate btEndDate = bladeTask.getEndDate();
            if (Objects.equals(bladeTask.getTestRig(), checkBladeTask.getTestRig()) &&
                    bladeTask.getId() != checkBladeTask.getId() &&
                    (btStartDate.isBefore(endDate) && btStartDate.isAfter(startDate) ||
                            btEndDate.isBefore(endDate) && btEndDate.isAfter(startDate) ||
                            startDate.isBefore(btEndDate) && startDate.isAfter(btStartDate) ||
                            endDate.isAfter(btStartDate) && endDate.isBefore(btEndDate) ||
                            btStartDate.isEqual(startDate) ||
                            btEndDate.isEqual(endDate)
                    )
            ) {
                return true;
            }
        }

        return false;
    }

    public void deleteBladeTask(Long id){
        BladeTask bladeTaskToDelete = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        //Remove old bookings
        bookingLogic.removeBookings(bladeTaskToDelete);
        System.out.println("Bookings deleted");

        //Finding all the related conflicts
        Set<Conflict> relatedConflicts = bladeTaskToDelete.getRelatedConflicts();
        Set<BladeTask> relatedBladeTasks = new HashSet<>();
        System.out.println("Lists created");

        //Removes the old relations on the bladetask that is being updated. This makes it possible to save the bladetask later
        bookingLogic.resetRelatedConflicts(bladeTaskToDelete);
        System.out.println("Related conflicts reset");

        //run through all the related conflicts to find the related bladetasks
        for (Conflict relatedConflict : relatedConflicts) {
            Booking tempBooking = relatedConflict.fetchBooking();
            relatedBladeTasks.add(tempBooking.fetchBladeTask());
        }

        //Deletes and recreates all the bookings on the related bladetasks
        for (BladeTask relatedBladeTask : relatedBladeTasks) {
            BladeTask tempBladeTask = bookingLogic.deleteAndRecreateBookings(relatedBladeTask);
            bladeTaskRepository.save(tempBladeTask);
        }
        bookingLogic.recalculateConflicts(bladeTaskToDelete);

        bladeTaskRepository.delete(bladeTaskToDelete);
        onDatabaseUpdate();

    }

}


