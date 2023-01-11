package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import org.springframework.core.io.Resource;

public interface ConvertedService {
    Convertables listConverted();

    Resource getResultingLipsyncOutput(String uuid);

    Resource getResultingWavInput(String uuid);

    void deleteResourcesForUuid(String uuid);

}
