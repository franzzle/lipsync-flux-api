package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ConversionService {

    public void convert(@ValidUuid String uuid){
        System.out.println(uuid);
    }

}
