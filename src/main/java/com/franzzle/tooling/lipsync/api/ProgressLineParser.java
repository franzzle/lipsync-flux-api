package com.franzzle.tooling.lipsync.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franzzle.tooling.lipsync.api.service.model.ProgressLine;
import org.springframework.stereotype.Component;

@Component
public class ProgressLineParser {
    ProgressLine parse(String jsonLineContent){
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final ProgressLine progressLine = objectMapper.readValue(jsonLineContent, ProgressLine.class);
            return progressLine;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
