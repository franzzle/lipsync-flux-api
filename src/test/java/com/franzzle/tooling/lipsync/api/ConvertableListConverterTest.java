package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.config.ConvertableListConverter;
import com.franzzle.tooling.lipsync.api.model.Convertable;
import com.franzzle.tooling.lipsync.api.model.Convertables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConvertableListConverterTest {
    private final ConvertableListConverter converter = new ConvertableListConverter();

    @DisplayName("Make list and verify json output")
    @Test
    void testConvert()  {
        Convertable c1 = new Convertable("1");
        Convertable c2 = new Convertable("2");
        Convertable c3 = new Convertable("3");
        List<Convertable> convertableList = Arrays.asList(c1, c2, c3);
        final Convertables convertables = new Convertables();
        convertables.setConvertables(convertableList);
        final String convert = converter.convert(convertables);
        assertTrue(convert.contains("convertables"));
    }
}

