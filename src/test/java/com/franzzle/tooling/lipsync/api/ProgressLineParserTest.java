package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.model.ProgressLine;
import com.franzzle.tooling.lipsync.api.util.ProgressLineParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ProgressLineParserTest {
    @DisplayName("Progress lines and verify start lines are parsed correctly")
    @Test
    public void parseStart() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("/progressLineStart.json");
        String jsonText = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));

        final ProgressLineParser progressLineParser = new ProgressLineParser();
        final ProgressLine progressLine = progressLineParser.parse(jsonText);
        Assertions.assertNotNull(progressLine);
        Assertions.assertNull(progressLine.getValue());
    }

    @DisplayName("Progress lines and verify progress lines that are response from Rhubarb")
    @Test
    public void parseProgress() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/progressLineProgress.json");
        String jsonText = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));

        final ProgressLineParser progressLineParser = new ProgressLineParser();
        final ProgressLine progressLine = progressLineParser.parse(jsonText);
        Assertions.assertNotNull(progressLine);
        Assertions.assertNotNull(progressLine.getValue());
    }

}
