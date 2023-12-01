package com.franzzle.tooling.lipsync.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UuidConversionException extends RuntimeException {
    public UuidConversionException(String message) {
        super(message);
    }
}
