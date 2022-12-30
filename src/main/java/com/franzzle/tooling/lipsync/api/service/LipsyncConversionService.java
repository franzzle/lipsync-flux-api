package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.validator.ValidUuid;

public interface LipsyncConversionService {
    void convert(@ValidUuid String uuid);
}
