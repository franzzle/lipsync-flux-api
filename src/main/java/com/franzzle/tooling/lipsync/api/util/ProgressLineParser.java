package com.franzzle.tooling.lipsync.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franzzle.tooling.lipsync.api.service.model.ProgressLine;
import org.springframework.stereotype.Component;

//TODO parse ProgressType
@Component
public class ProgressLineParser {
    public ProgressLine parse(String jsonLineContent) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonLineContent, ProgressLine.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
