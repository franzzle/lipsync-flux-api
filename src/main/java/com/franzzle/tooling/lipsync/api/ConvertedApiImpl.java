package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.service.ConvertedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConvertedApiImpl implements ConvertedApi{
    @Autowired
    private ConvertedService convertedService;

    @Override
    public Mono<Convertables> listConverted() {
        return Mono.just(convertedService.listConverted());
    }
}
