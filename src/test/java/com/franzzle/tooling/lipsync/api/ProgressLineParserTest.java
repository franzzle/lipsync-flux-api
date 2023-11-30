package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.model.ProgressLine;
import com.franzzle.tooling.lipsync.api.util.ProgressLineParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ProgressLineParserTest {
    @Test
    public void parseStart() throws Exception{
        String jsonProgressFile = ClassLoader.getSystemResource("progressLineStart.json").getFile();
        String jsonText = Files.readString(Paths.get(jsonProgressFile));

        final ProgressLineParser progressLineParser = new ProgressLineParser();
        final ProgressLine progressLine = progressLineParser.parse(jsonText);
        Assertions.assertNotNull(progressLine);
    }

    @Test
    public void parseProgress() throws Exception{
        String jsonProgressFile = ClassLoader.getSystemResource("progressLineProgress.json").getFile();
        String jsonText = Files.readString(Paths.get(jsonProgressFile));

        final ProgressLineParser progressLineParser = new ProgressLineParser();
        final ProgressLine progressLine = progressLineParser.parse(jsonText);
        Assertions.assertNotNull(progressLine);
        Assertions.assertNotNull(progressLine.getValue());
    }



}
