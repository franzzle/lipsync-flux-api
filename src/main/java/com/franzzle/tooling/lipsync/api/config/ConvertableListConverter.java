package com.franzzle.tooling.lipsync.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.franzzle.tooling.lipsync.api.Convertable;

import java.util.List;

public class ConvertableListConverter extends StdConverter<List<Convertable>, String> {

    @Override
    public String convert(List<Convertable> convertables)  {
        final StringBuilder builder = new StringBuilder("[");
        convertables.stream().map(value -> {
                    try {
                        return new ObjectMapper().writeValueAsString(value);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(urnStr -> {
            if(builder.length() > 1) {
                builder.append(", ");
            }

            builder.append("\"").append(urnStr).append("\"");
        });

        builder.append("]");
        return builder.toString();
    }
}
