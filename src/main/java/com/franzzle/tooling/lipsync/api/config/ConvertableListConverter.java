package com.franzzle.tooling.lipsync.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.franzzle.tooling.lipsync.api.Convertables;

public class ConvertableListConverter extends StdConverter<Convertables, String> {

    @Override
    public String convert(Convertables convertables) {
        try {
            return new ObjectMapper().writeValueAsString(convertables);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
