package com.franzzle.tooling.lipsync.api.openapi;

import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import com.franzzle.tooling.lipsync.api.service.RhubarbService;
import com.franzzle.tooling.lipsync.api.service.SinkWrapper;
import com.franzzle.tooling.lipsync.api.sink.SinkWrapperRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class ConversionEventsApiImpl implements ConversionEventsApi {
    @Autowired
    private RhubarbService rhubarbService;

    @Autowired
    private LipsyncConversionService lipsyncConversionService;

    @Autowired
    private SinkWrapperRegistry sinkWrapperRegistry;


    public Flux<String> getEventsAsTheyHappen(String uuid) {
        final SinkWrapper sink = sinkWrapperRegistry.getSink(uuid);
        return sink.getSink().asFlux();
    }
}
