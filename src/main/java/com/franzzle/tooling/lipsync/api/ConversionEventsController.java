package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.RhubarbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class ConversionEventsController {
    @Autowired
    private RhubarbService rhubarbService;

    @GetMapping(value = "/conversion/{uuid}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEventsAsTheyHappen(@PathVariable String uuid) {
        return Flux.interval(Duration.ofSeconds(1))
//                .log()
                .map(time -> String.format("%d occurred", time));
    }
}
