package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import org.springframework.core.io.Resource;

public interface ConvertedService {
    Convertables listConverted();

    Resource getResourceForUuid(String uuid);


}
