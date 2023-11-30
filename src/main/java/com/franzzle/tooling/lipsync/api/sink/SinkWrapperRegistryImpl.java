package com.franzzle.tooling.lipsync.api.sink;

import com.franzzle.tooling.lipsync.api.service.SinkWrapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SinkWrapperRegistryImpl implements SinkWrapperRegistry {
    private Map<String, SinkWrapper> sinkWrapperRegistry = new HashMap<>();

    @Override
    public SinkWrapper addSink(String uuid){
        final SinkWrapper sinkWrapper = new SinkWrapper(uuid);
        sinkWrapperRegistry.put(uuid, sinkWrapper);
        return sinkWrapper;
    }

    @Override
    public SinkWrapper getSink(String uuid){
        return sinkWrapperRegistry.get(uuid);
    }

    @Override
    public void removeSink(String uuid) {
        if(sinkWrapperRegistry.containsKey(uuid)){
            sinkWrapperRegistry.remove(uuid);
        }
    }
}
