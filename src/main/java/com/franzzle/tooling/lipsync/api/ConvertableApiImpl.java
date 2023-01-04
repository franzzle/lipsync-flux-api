package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.error.ApiException;
import com.franzzle.tooling.lipsync.api.error.UuidConversionException;
import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.*;

@RestController
@Tag(name = "conversion", description = "conversion to lipsync wav")
public class ConvertableApiImpl implements ConvertableApi {

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Autowired
    private LipsyncConversionService lipsyncConversionService;

    @Autowired
    private SinkWrapperRegistry sinkWrapperRegistry;

    @Override
    public Mono<Convertable> postFile(Mono<FilePart> filePartMono) {
        final String uuid = UUID.randomUUID().toString().toUpperCase();
        final File dest = new File(storageDir, String.format("%s.wav", uuid));
        return filePartMono
                .doFirst(() -> System.out.println(String.format("Conversion started")))
                .doOnNext(filePart -> System.out.println(filePart.filename()))
                .doFinally(signalType -> System.out.println(String.format("Conversion ended")))
                .flatMap(filePart -> filePart.transferTo(dest))
                .thenReturn(new Convertable(uuid));
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
        final List<String> filenames =
                Arrays.stream(Objects.requireNonNull(new File(storageDir)
                                .list((dir, name) -> name.endsWith("wav"))))
                        .toList();


        final Convertables convertables = new Convertables();
        convertables.setConvertables(new ArrayList<>());

        for (String uuidFileName : filenames) {
            final String uuidFromFile = getFileNameWithoutExtension(uuidFileName);
            Convertable convertable = new Convertable(uuidFromFile);
            convertables.getConvertables().add(convertable);
        }
        return convertables;
    }


    public static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }

    private static String getUuidFileNameFormatted(String filename) {
        return String.format("%s.wav", filename);
    }
}
