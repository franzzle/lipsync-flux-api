package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.service.model.RhubarbDTO;
import io.reactivex.Observable;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.ClassLoader.getSystemResource;

//TODO Output to temp location and cleanup after that
@Disabled
@SpringJUnitConfig
@ContextConfiguration(classes = RhubarbServiceTest.ContextConfiguration.class)
@TestPropertySource(properties =
        {
                "app.rhubarb.dir=/Users/franzzle/Development/Tooling/rhubarb-lip-sync-1.10.0-osx"
        }
)
public class RhubarbServiceTest {
    @Autowired
    private RhubarbService rhubarbService;

    @Captor
    private ArgumentCaptor<OutputStream> bufferedOutputStreamArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private RhubarbDTO rhubarbDTO;

    static class ContextConfiguration {
        @Bean
        RhubarbService rhubarbService() {
            return new RhubarbServiceImpl();
        }
    }

    @BeforeEach
    public void setupRhubarbDto(){
        rhubarbDTO = getRhubarbDTO();
    }


    @DisplayName("Convert a spoken text as a WAV file into a lipsinc queue json file and verify that it converted normally")
    @Test
    public void waveToLipSync() throws Exception {
        final Observable<String> observableLipsyncProcess = rhubarbService.waveTolipSync(rhubarbDTO);
        final List<String> lastProgressLineOutput = new ArrayList<>();
        observableLipsyncProcess.subscribe(
                progressStatusLine -> {
                    System.out.println(progressStatusLine);
                    lastProgressLineOutput.add(progressStatusLine);
                },
                throwable -> Assertions.fail(throwable.getMessage()),
                () -> {
                    final String lastLineInList = lastProgressLineOutput.get(lastProgressLineOutput.size() - 1);
                    Assertions.assertTrue(lastLineInList.contains("Application terminating normally."));
                });

        Assertions.assertFalse(lastProgressLineOutput.isEmpty(), "Should contain at least some lines");

        final File jsonFile = new File(rhubarbDTO.getDestinationOuputPath(), String.format("%s.json", rhubarbDTO.getSourceUuid()));
        String lipsyncJson = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));

        Assertions.assertNotNull(lipsyncJson, "Should at least return something!");
        Assertions.assertTrue(lipsyncJson.contains("mouthCues"), "Should contain an arrat of mouthCues");
    }

    @Test
    public void waveToLipSyncWithRepresentingText() throws Exception {
        rhubarbDTO.setSpokenTextHint("That's one of those typical gravestones");

        Observable<String> observableLipsyncProcess = rhubarbService.waveTolipSync(rhubarbDTO);
        final List<String> lastProgressLineOutput = new ArrayList<>();
        observableLipsyncProcess.subscribe(
                progressStatusLine -> {
                    lastProgressLineOutput.add(progressStatusLine);
                },
                throwable -> Assertions.fail(throwable.getMessage()),
                () -> {
                    final String lastLineInList = lastProgressLineOutput.get(lastProgressLineOutput.size() - 1);
                    Assertions.assertTrue(lastLineInList.contains("Application terminating normally."));
                });

    }

    private static RhubarbDTO getRhubarbDTO() {
        final RhubarbDTO rhubarbDTO = new RhubarbDTO();
        rhubarbDTO.setSourceUuid("F83DF837-66B4-43CC-AD0A-677AE0AAB809");
        rhubarbDTO.setSourceInputPath(new File(getSystemResource(String.format("%s.wav", rhubarbDTO.getSourceUuid())).getFile()).getParent());
        rhubarbDTO.setDestinationOuputPath(new File(System.getProperty("java.io.tmpdir"), "destLipsyncSequences").getAbsolutePath());
        return rhubarbDTO;
    }
}
