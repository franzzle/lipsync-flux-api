package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import com.franzzle.tooling.lipsync.api.service.SinkWrapper;
import com.franzzle.tooling.lipsync.api.service.RhubarbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ConversionEventsController {
    @Autowired
    private RhubarbService rhubarbService;

    @Autowired
    private LipsyncConversionService lipsyncConversionService;

    @GetMapping(value = "/conversion/{uuid}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEventsAsTheyHappen(@PathVariable String uuid) {
        final SinkWrapper sink = lipsyncConversionService.getSink(uuid);
        return sink.getSink().asFlux();
    }
}
