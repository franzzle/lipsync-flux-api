package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.validator.ValidUuid;

public interface LipsyncConversionService {
    Void convert(@ValidUuid String uuid);

//    SinkWrapper getSink(String uuid);
}
