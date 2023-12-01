package com.franzzle.tooling.lipsync.api.openapi;

import com.franzzle.tooling.lipsync.api.error.ApiException;
import com.franzzle.tooling.lipsync.api.error.UuidConversionException;
import com.franzzle.tooling.lipsync.api.model.Convertable;
import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import com.franzzle.tooling.lipsync.api.sink.SinkWrapperRegistry;
import com.franzzle.tooling.lipsync.api.util.FileUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ConvertableApiImpl implements ConvertableApi {

    @Value("${wav.storage.dir:#{systemProperties['java.io.tmpdir']}/wavStorageDir}")
    private String storageDir;

    @Autowired
    private Environment environment;

    @Autowired
    private LipsyncConversionService lipsyncConversionService;

    @Autowired
    private FileUtilities fileUtilities;

    @Autowired
    private SinkWrapperRegistry sinkWrapperRegistry;

    @Override
    public Mono<Convertable> postFile(Mono<FilePart> filePartMono) {
        final String uuid = UUID.randomUUID().toString().toUpperCase();

        final File wavStorageDir = getWavStorageDir();

        final File dest = new File(wavStorageDir, String.format("%s.wav", uuid));
        return filePartMono
                .doFirst(() -> System.out.println("Conversion started"))
                .doOnNext(filePart -> System.out.println(filePart.filename()))
                .doFinally(signalType -> System.out.println(String.format("Conversion ended")))
                .flatMap(filePart -> filePart.transferTo(dest))
                .thenReturn(new Convertable(uuid));
    }

    private File getWavStorageDir() {
        final File wavStorageDir = new File(storageDir);
        if(!environment.containsProperty("wav.storage.dir")){
            System.out.println(String.format("Setting default DIR : %s", storageDir));
        }
        if(!wavStorageDir.exists()) {
            boolean mkdirs = wavStorageDir.mkdirs();
            if(!mkdirs){
                System.out.println(String.format("Could not create directory %s", wavStorageDir.getAbsolutePath()));
            }
        }
        return wavStorageDir;
    }


    @Override
    public void deleteLipsyncArtifacts(String uuid) {
//        checkIfFileExists(uuid);
//        conversionService.convert(uuid);

    }


    @Override
    public Convertable putConversion(String uuid) {
        checkIfFileExists(uuid);
        sinkWrapperRegistry.addSink(uuid);

        Mono.fromCallable(() -> lipsyncConversionService.convert(uuid))
                .publishOn(Schedulers.elastic())
                .subscribe();

        return new Convertable(uuid);
    }

    private static void checkIfUuidIsGenuine(String uuid) {
        if (!isUUID(uuid)) {
            throw new UuidConversionException(String.format("%s is not a valid UUID and this conversion cannot start", uuid));
        }
    }

    private void checkIfFileExists(String uuid) {
        final String uuidFileNameFormatted = getUuidFileNameFormatted(uuid);
        final File wavFile = new File(storageDir, uuidFileNameFormatted);
        if (!wavFile.exists()) {
            throw new ApiException(String.format("%s is not existent on the filesystem", uuidFileNameFormatted));
        }
    }

    public static boolean isUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Mono<Convertables> list() {
        return Mono.just(getConvertables());
    }

    private Convertables getConvertables() {
        final File wavStorageDir = new File(storageDir);
        if(!wavStorageDir.exists()){
            final Convertables convertables = new Convertables();
            convertables.setConvertables(new ArrayList<>());
            return convertables;
        }

        final List<String> filenames =
                Arrays.stream(Objects.requireNonNull(wavStorageDir
                                .list((dir, name) -> name.endsWith("wav")))).collect(Collectors.toList());

        final Convertables convertables = new Convertables();
        convertables.setConvertables(new ArrayList<>());

        for (String uuidFileName : filenames) {
            final String uuidFromFile = fileUtilities.getFileNameWithoutExtension(uuidFileName);
            Convertable convertable = new Convertable(uuidFromFile);
            convertables.getConvertables().add(convertable);
        }
        return convertables;
    }

    private static String getUuidFileNameFormatted(String filename) {
        return String.format("%s.wav", filename);
    }
}
