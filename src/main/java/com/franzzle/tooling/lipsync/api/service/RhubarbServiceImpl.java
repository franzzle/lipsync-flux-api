package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.service.model.RhubarbDTO;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.apache.commons.exec.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@Component
public class RhubarbServiceImpl implements RhubarbService {
    public static final int FOUR_MINUTES = 4 * 60 * 1000;
    public static final String TEMPORARY_WAVFILE_FOR_UUID = "temporaryWavfileForUuid";
    public static final String TEMPORARY_TEXT_FILE_REPRESENTING_WAV = "temporaryTextFileRepresentingWav";

    @Autowired
    private Environment environment;

    @Value("${app.rhubarb.dir:/rhubarb}")
    private String rhubarbDir;

    @Override
    public Observable<String> wavTolipSync(RhubarbDTO rhubarbDTO) {
        return getObservableForLipsyncProcess(rhubarbDTO.getSourceUuid(),
                rhubarbDTO.getSourceInputPath(),
                rhubarbDTO.getDestinationOuputPath(),
                this.rhubarbDir,
                rhubarbDTO.getSpokenTextHint());
    }

    private Observable<String> getObservableForLipsyncProcess(String uuid,
                                                              String sourceDir,
                                                              String destDir,
                                                              String rhubarbToolDir,
                                                              String optionalTextWavRepresents) {
        return Observable.create((ObservableEmitter<String> emitter) -> {
            final String uuidWavFile = String.format("%s.wav", uuid);
            final File sourceFileDir = new File(sourceDir);
            if (sourceFileDir.isFile()) {
                throw new RuntimeException(String.format("Given sourcedir %s is a file and not a directory", sourceFileDir.getAbsolutePath()));
            }

            final File[] filesInSource = sourceFileDir.listFiles();


            boolean exists = Arrays.stream(filesInSource)
                    .anyMatch(file -> file.getName().contains(uuid));

            if (exists) {
                final File temporaryWavfileForUuid = new File(sourceDir, uuidWavFile);
                CommandLine cmdLine = new CommandLine(rhubarbToolDir + "/rhubarb");

                final File jsonDestinationDir = new File(destDir);
                if (!jsonDestinationDir.exists()) {
                    jsonDestinationDir.mkdirs();
                }

                HashMap substitionMapToBuild = new HashMap();
                if (optionalTextWavRepresents != null) {
                    final File textFileThatRepresentsWav = new File(sourceDir, String.format("%s.txt", uuid));
                    final Path pathToTextRepresentingWav = Paths.get(textFileThatRepresentsWav.toURI());
                    Files.write(pathToTextRepresentingWav, optionalTextWavRepresents.getBytes(StandardCharsets.UTF_8));
                    cmdLine.addArgument("-d");
                    cmdLine.addArgument(String.format("${%s}", TEMPORARY_TEXT_FILE_REPRESENTING_WAV));
                    substitionMapToBuild.put(TEMPORARY_TEXT_FILE_REPRESENTING_WAV, textFileThatRepresentsWav);
                }
                cmdLine.addArgument("-f");
                cmdLine.addArgument("json");
                cmdLine.addArgument(String.format("${%s}", TEMPORARY_WAVFILE_FOR_UUID));
                cmdLine.addArgument("--machineReadable");
                cmdLine.addArgument("-o");
                final String absoluteDestination = new File(jsonDestinationDir, String.format("%s.json", uuid)).getAbsolutePath();
                cmdLine.addArgument(absoluteDestination);

                substitionMapToBuild.put(TEMPORARY_WAVFILE_FOR_UUID, temporaryWavfileForUuid);
                cmdLine.setSubstitutionMap(substitionMapToBuild);

                final DefaultExecutor executor = new DefaultExecutor();

                final ExecuteWatchdog watchdog = new ExecuteWatchdog(FOUR_MINUTES);
                executor.setWatchdog(watchdog);

                final PipedOutputStream processOutputStream = new PipedOutputStream();
                final PipedInputStream pipedInputStream = new PipedInputStream(processOutputStream);
                final PumpStreamHandler streamHandler = new PumpStreamHandler(processOutputStream);
                executor.setStreamHandler(streamHandler);
                CountDownLatch latch = new CountDownLatch(1);
                try {
                    new Thread(() -> {
                        try (final InputStreamReader in = new InputStreamReader(pipedInputStream);
                             final BufferedReader reader = new BufferedReader(in)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                emitter.onNext(line);
                            }
                            latch.countDown();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();

                    executor.execute(cmdLine);
                } catch (ExecuteException e) {
                    emitter.onError(e);
                    return;
                }
                latch.await();
                processOutputStream.close();
                emitter.onComplete();
            }
        });
    }
}
