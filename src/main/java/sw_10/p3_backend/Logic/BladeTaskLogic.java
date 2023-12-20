package sw_10.p3_backend.Logic;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import sw_10.p3_backend.Model.*;
import sw_10.p3_backend.Repository.BladeProjectRepository;
import sw_10.p3_backend.Repository.BladeTaskRepository;
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
    } //Sets

    /**
     * Creates a new BladeTask from the provided BladeTaskInput object
     * @param input The BladeTaskInput object containing the data for the new BladeTask
     * @return The newly created BladeTask
     */
    public BladeTask createBladeTask(BladeTaskInput input) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        validateBladeTaskInput(input);

        // Find the blade project in the database
        BladeProject bladeProject = getBladeProject(Long.valueOf(input.bladeProjectId()));
        Integer totalDuration = input.duration() + input.attachPeriod() + input.detachPeriod();

        if(input.testRig()==0 ) {

            // Create a new BladeTask instance
            BladeTask newBladeTask = new BladeTask(
                    null,
                    null,
                    totalDuration,
                    input.testType(),
                    input.attachPeriod(),
                    input.detachPeriod(),
                    input.taskName(),
                    0,
                    bladeProject
            );
            // Create resource orders for the blade task (if any)
            List<ResourceOrder> resourceOrders = handleResourceOrders(input, newBladeTask);


            // Save the new BladeTask in the database
            bladeTaskRepository.save(newBladeTask);
            onDatabaseUpdate();
            return newBladeTask;
        }

        LocalDate startDate = input.startDate();

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
                newBladeTask)) {
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

    /**
     * Finds the blade project with the provided ID in the database
     * @param bladeProjectId The ID of the blade project to find
     * @return The blade project with the provided ID
     * @throws NotFoundException If no blade project with the provided ID exists in the database
     */
    private BladeProject getBladeProject(Long bladeProjectId) {
        return bladeProjectRepository.findById(bladeProjectId)
                .orElseThrow(() -> new NotFoundException("BladeProject not found with ID: " + bladeProjectId));
    }

    /**
     * Creates resource orders for the provided blade task if any are provided in the BladeTaskInput object
     * @param input The BladeTaskInput object containing the resource orders to create
     * @param newBladeTask The blade task to create the resource orders for
     * @return The list of created resource orders
     */
    private @Nullable List<ResourceOrder> handleResourceOrders(BladeTaskInput input, BladeTask newBladeTask) {
        if (input.resourceOrders() != null) {
            return resourceOrderLogic.createResourceOrdersBladeTask(input.resourceOrders(), newBladeTask);
        }
        return null;
    }

    /**
     * Finds all blade tasks in the database
     * @return A list of all blade tasks in the database
     */
    public List<BladeTask> findAll() {
        return bladeTaskRepository.findAll();
    }

    /**
     * Finds the blade task with the provided ID in the database
     * @param id The ID of the blade task to find
     * @return The blade task with the provided ID
     * @throws NotFoundException If no blade task with the provided ID exists in the database
     */
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

    /**
     * Validates the provided BladeTaskInput object
     * @param input The BladeTaskInput object to validate
     * @throws InputInvalidException If the provided BladeTaskInput object is invalid
     */
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
    }

    /**
     * Calculates the end date of a blade task based on the provided start date and duration
     * @param startDate The start date of the blade task
     * @param duration The duration of the blade task
     * @return The end date of the blade task
     */
    private LocalDate calculateEndDate(LocalDate startDate, Integer duration) {
        if (startDate != null && duration != null) {
            return startDate.plusDays(duration - 1);
        }
        return null;
    }

    /**
     * Updates the start date and duration of the blade task with the provided ID
     * This method also deletes and recreates bookings for the blade task
     * and all related blade tasks in order to update conflict
     * @param id The ID of the blade task to update
     * @param startDate The new start date of the blade task
     * @param duration The new duration of the blade task
     * @param testRig The new test rig of the blade task
     * @return The updated blade task
     */
    public BladeTask updateStartAndDurationBladeTask(Long id, String startDate, Integer duration, Integer testRig) {
        // Validate input here (e.g., check for mandatory fields other than startDate and testRig)
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        //Remove old bookings
        bookingLogic.removeBookingsBladeTask(bladeTaskToUpdate);

        //Finding all the related conflicts
        Set<Conflict> relatedConflicts = bladeTaskToUpdate.getRelatedConflicts();
        Set<BladeTask> relatedBladeTasks = new HashSet<>();

        //Removes the old relations on the bladetask that is being updated. This makes it possible to save the bladetask later
        bookingLogic.resetRelatedConflicts(bladeTaskToUpdate);

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


        // Save the new BladeTask in the database. This creates problems, if you do not reset the relatedConflicts after deleting them
        bladeTaskRepository.save(bladeTaskToUpdate);

        // Create bookings for the blade task if the blade task is assigned to a test rig and resource orders are provided
        if (testRigValue != 0 && bladeTaskToUpdate.getResourceOrders() != null) {
            bookingLogic.createBookings(bladeTaskToUpdate.getResourceOrders(), bladeTaskToUpdate);
        }

        //Checks if the start date and end date of the bladeproject should change and then saves the bladetask
        bladeProjectLogic.updateStartAndEndDate(bladeTaskToUpdate.getBladeProject());
        bladeTaskRepository.save(bladeTaskToUpdate);

        //Ensures that older conflicts are updated with the related bladetasks
        bookingLogic.recalculateConflicts(bladeTaskToUpdate);

        //Sends the updates to the clients
        onDatabaseUpdate();
        // Return the new BladeTask
        return bladeTaskToUpdate;
    }

    /**
     * Finds all blade tasks in the database that are pending
     * @param isActive Whether to find pending blade tasks from view schedule or edit schedule
     * @return A list of all blade tasks in the database that are pending from the provided schedule
     */
    public List<BladeTask> bladeTasksInRange(String startDate, String endDate, boolean isActive) {
        return bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive);
    }


    /**
     * Updates the blade task with the provided ID with the provided values in the BladeTaskInput object
     * @param updates The BladeTaskInput object containing the values to update
     * @param btId The ID of the blade task to update
     * @return The updated blade task
     */
    public BladeTask updateBTInfo(BladeTaskInput updates, Long btId) {
        BladeTask bladeTaskToUpdate = bladeTaskRepository.findById(btId)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + btId));

        int totalDuration = updates.duration() + updates.attachPeriod() + updates.detachPeriod();

        LocalDate endDate = calculateEndDate(updates.startDate(), totalDuration);

        List<BladeTask> bladeTasksInRange = bladeTaskRepository.bladeTasksInRange(updates.startDate(), endDate, false);

        //Checks for overlap with other bladetasks
        if (bladeTaskToUpdate.getStartDate() != updates.startDate()
                || bladeTaskToUpdate.getEndDate() != endDate) {
            if (checkForBTOverlap(updates.startDate(), endDate, bladeTaskToUpdate)) {
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

        resourceOrderLogic.removeResourceOrdersBladeTask(bladeTaskToUpdate);
        resourceOrderLogic.createResourceOrdersBladeTask(updates.resourceOrders(), bladeTaskToUpdate);

        bladeTaskRepository.save(bladeTaskToUpdate);
        bladeProjectLogic.updateStartAndEndDate(bladeTaskToUpdate.getBladeProject());

        bladeTaskToUpdate = updateStartAndDurationBladeTask((long) bladeTaskToUpdate.getId(), bladeTaskToUpdate.getStartDate().toString(), bladeTaskToUpdate.getDuration(), bladeTaskToUpdate.getTestRig());

        return bladeTaskToUpdate;
    }

    /**
     * Finds all conflicts for the blade task with the provided ID
     * @param id The ID of the blade task to find conflicts for
     * @param isActive Whether to find conflicts from view schedule or edit schedule
     * @return A list of all conflicts for the blade task with the provided ID
     * @throws NotFoundException
     */
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



    /**
     * Finds all blade tasks in the database that are pending and creates a Flux stream to emit the list
     * of blade tasks in a given range to subscribers whenever an update occurs. Updates are handle using
     * onDatabaseUpdate whenever this is called in the code blade tasks will be emitted to subscribers.
     * @param startDate
     * @param endDate
     * @param isActive
     * @return
     */
    public Flux<List<BladeTask>> bladeTasksInRangeSub(String startDate, String endDate, boolean isActive) {
        // Create a Flux stream to emit the list of blade tasks in a given range when to subscribers whenever an update occurs.
        return createFlux(() -> bladeTaskRepository.bladeTasksInRange(LocalDate.parse(startDate), LocalDate.parse(endDate), isActive));
        //The call to the repository is the supplier that provides the actual data (list of BladeTasks) to be emitted by the Flux.
    }


    /**
     * Finds all blade tasks in the database that are pending and creates a Flux stream to emit the list
     * of blade tasks pending to subscribers whenever an update occurs. Updates are handle using
     *      * onDatabaseUpdate whenever this is called in the code blade tasks will be emitted to subscribers.
     * @param isActive
     * @return
     */
    public Flux<List<BladeTask>> bladeTasksPendingSub(boolean isActive) {
        // Create a Flux stream to emit the list of blade tasks pending when to subscribers whenever an update occurs.
        return createFlux(() -> bladeTaskRepository.bladeTasksPending(isActive));
    }


    /**
     * This method creates a Flux stream based on a supplier. The supplier provides the actual data (list of BladeTasks)
     * to be emitted by the Flux. The Flux stream is shared to keep it active.
     * @param supplier The supplier that provides the actual data (list of BladeTasks) to be emitted by the Flux.
     * @return A Flux stream that emits the list of BladeTasks provided by the supplier.
     */
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

    /**
     * Emits a signal to all subscribers that the database has been updated
     */
    public void onDatabaseUpdate() {
        //Updates the processor with a new object to trigger a signal emission to subscribers that will run the query again
        processor.tryEmitNext(new Object());
    }


    /**
     * Finds all blade tasks that has a booking of a resource with the provided name in the provided period
     * @param equipmentName The name of the resource to find blade tasks for
     * @param startDate The start date of the period to find blade tasks for
     * @param endDate The end date of the period to find blade tasks for
     * @return A list of all blade tasks that has a booking of a resource with the provided name in the provided period
     */
    public List<BladeTask> getRelatedBladeTasksByEquipmentType(String equipmentName, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findBookedEquipmentByTypeAndPeriod(equipmentName, startDate, endDate);

        List<BladeTask> bladeTasks = new ArrayList<>();
        for (Booking booking : bookings) {
            BladeTask tempBladeTask = booking.fetchBladeTask();
            bladeTasks.add(tempBladeTask);
        }
        return bladeTasks;
    }

    /**
     * This method checks for overlap between blade tasks in the provided period
     * @param startDate The start date of the period to check for overlap
     * @param endDate The end date of the period to check for overlap
     * @param checkBladeTask The blade task to check for overlap with
     * @return Whether the provided blade task overlaps with any other blade tasks in the provided period
     */
    private boolean checkForBTOverlap(LocalDate startDate, LocalDate endDate, BladeTask checkBladeTask) {

        List<BladeTask> bladeTasksInRange = bladeTaskRepository.bladeTasksInRange(startDate, endDate, false);

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

    /**
     * Deletes the blade task with the provided ID
     * @param id The ID of the blade task to delete
     */
    public void deleteBladeTask(Long id) {
        BladeTask bladeTaskToDelete = bladeTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BladeTask not found with ID: " + id));

        //Remove old bookings
        bookingLogic.removeBookingsBladeTask(bladeTaskToDelete);

        //Finding all the related conflicts
        Set<Conflict> relatedConflicts = bladeTaskToDelete.getRelatedConflicts();
        Set<BladeTask> relatedBladeTasks = new HashSet<>();

        //Removes the old relations on the bladetask that is being updated. This makes it possible to save the bladetask later
        bookingLogic.resetRelatedConflicts(bladeTaskToDelete);

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
        bladeProjectLogic.updateStartAndEndDate(bladeTaskToDelete.getBladeProject());
        onDatabaseUpdate();

    }

}


