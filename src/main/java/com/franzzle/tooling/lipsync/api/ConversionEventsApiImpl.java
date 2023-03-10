package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import com.franzzle.tooling.lipsync.api.service.RhubarbService;
import com.franzzle.tooling.lipsync.api.service.SinkWrapper;
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
        //TODO Throw Mono error 404 if nothing found
        System.out.println(String.format("Calling eventcontroller for %s", uuid));

        final SinkWrapper sink = sinkWrapperRegistry.getSink(uuid);
        return sink.getSink().asFlux();
    }
}
