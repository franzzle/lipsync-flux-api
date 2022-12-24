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
    public Observable<String> waveTolipSync(RhubarbDTO rhubarbDTO) {
        return getObservableForLipsyncProcess(rhubarbDTO.getSourceInputFile(),
                rhubarbDTO.getSourceInputPath(),
                rhubarbDTO.getDestPath(),
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
            final File[] filesInSource = new File(sourceDir).listFiles();
            boolean exists = Arrays.stream(filesInSource)
                    .anyMatch(file -> file.getName().contains(uuid));

            if(exists){
                final File temporaryWavfileForUuid = new File(sourceDir, uuidWavFile);
                CommandLine cmdLine = new CommandLine(rhubarbToolDir + "/rhubarb");

                final File jsonDestinationDir = new File(destDir);
                if(!jsonDestinationDir.exists()){
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

                try (PipedOutputStream processOutputStream = new PipedOutputStream();
                     final PipedInputStream pipedInputStream = new PipedInputStream(processOutputStream)) {
                    PumpStreamHandler streamHandler = new PumpStreamHandler(processOutputStream);
                    executor.setStreamHandler(streamHandler);

                    try {
                        new Thread(() -> {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(pipedInputStream));
                            try {
                                for (String line; (line = reader.readLine()) != null; ) {
                                    emitter.onNext(line);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();

                        executor.execute(cmdLine);

                    } catch (ExecuteException e) {
                        emitter.onError(e);
                        return;
                    }
                    emitter.onComplete();
                }
            }
        });
    }
}
