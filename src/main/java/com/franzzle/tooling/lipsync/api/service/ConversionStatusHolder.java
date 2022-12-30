package com.franzzle.tooling.lipsync.api.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class ConversionStatusHolder {
    private final Sinks.Many sink;

    public ConversionStatusHolder() {
        sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Sinks.Many getSink() {
        return sink;
    }
}
