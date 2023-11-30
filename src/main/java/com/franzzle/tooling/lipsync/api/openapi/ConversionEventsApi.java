package com.franzzle.tooling.lipsync.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@Tag(name = "conversion-events", description = "Handles conversion events to track progress")
public interface ConversionEventsApi {
    @Operation(summary = "Handles text events indicating the status of the lipsync conversion",
            tags = {"conversion-events"})
    @GetMapping(value = "/conversion/{uuid}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> getEventsAsTheyHappen(@PathVariable String uuid);
}
