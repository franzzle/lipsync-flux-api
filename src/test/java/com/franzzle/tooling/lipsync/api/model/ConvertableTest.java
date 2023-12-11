package com.franzzle.tooling.lipsync.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConvertableTest {
    @Test
    public void parse() throws JsonProcessingException {
        String responseJson ="{\"uuid\":\"C32C6027-FA5A-4C4A-8ACE-3B4CB0D7B901\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        Convertable convertable = objectMapper.readValue(responseJson, Convertable.class);

        Assertions.assertNotNull(convertable);

    }
}
