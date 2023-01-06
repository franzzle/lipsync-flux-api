package com.franzzle.tooling.lipsync.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

public interface ConversionEventsApi {
    @GetMapping(value = "/conversion/{uuid}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> getEventsAsTheyHappen(@PathVariable String uuid);
}
