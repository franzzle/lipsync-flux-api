package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.SinkWrapper;

public interface SinkWrapperRegistry {
    SinkWrapper addSink(String uuid);

    SinkWrapper getSink(String uuid);

    void removeSink(String uuid);
}
