package com.franzzle.tooling.lipsync.api.service;

import reactor.core.publisher.Sinks;

public class SinkWrapper {
    private final Sinks.Many sink;
    private final String uuid;

    public SinkWrapper(String uuid) {
        this.uuid = uuid;
        sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Sinks.Many getSink() {
        return sink;
    }
}
