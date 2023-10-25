package com.franzzle.tooling.lipsync.api.sink;

import com.franzzle.tooling.lipsync.api.service.SinkWrapper;

public interface SinkWrapperRegistry {
    SinkWrapper addSink(String uuid);

    SinkWrapper getSink(String uuid);

    void removeSink(String uuid);
}
