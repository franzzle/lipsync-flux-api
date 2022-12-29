package com.franzzle.tooling.lipsync.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franzzle.tooling.lipsync.api.config.ConvertableListConverter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvertableListConverterTest {
    private final ConvertableListConverter converter = new ConvertableListConverter();

    @Test
    void testConvert() throws JsonProcessingException {
        Convertable c1 = new Convertable("1");
        Convertable c2 = new Convertable("2");
        Convertable c3 = new Convertable("3");
        List<Convertable> convertableList = Arrays.asList(c1, c2, c3);
        final Convertables convertables = new Convertables();
        convertables.setConvertables(convertableList);
        String expected = "[\"" + new ObjectMapper().writeValueAsString(c1) + "\", \"" +
                new ObjectMapper().writeValueAsString(c2) + "\", \"" +
                new ObjectMapper().writeValueAsString(c3) + "\"]";
        assertEquals(expected, converter.convert(convertables));
    }
}

