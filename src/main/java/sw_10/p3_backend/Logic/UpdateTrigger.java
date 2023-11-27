package sw_10.p3_backend.Logic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;

public class UpdateTrigger {
    private final DirectProcessor<Void> processor;
    private final FluxSink<Void> sink;

    public UpdateTrigger() {
        this.processor = DirectProcessor.create();
        this.sink = processor.sink();
    }

    public void triggerUpdate() {
        sink.next(null); // Emit an update event
    }

    public Flux<Void> getUpdateSignal() {
        return processor; // Flux that emits on each update
    }
}