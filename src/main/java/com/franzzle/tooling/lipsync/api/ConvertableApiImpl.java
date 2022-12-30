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

import java.io.File;
import java.util.*;

@RestController
@Tag(name = "conversion", description = "conversion to lipsync wav")
public class ConvertableApiImpl implements ConvertableApi {

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Autowired
    private LipsyncConversionService lipsyncConversionService;



    @Override
    public Mono<Void> postFile(Mono<FilePart>  filePartMono) {
        return filePartMono
                .doFirst(() -> System.out.println(String.format("Conversion started")))
                .doOnNext(filePart -> System.out.println(filePart.filename()))
                .doFinally(signalType -> System.out.println(String.format("Conversion ended")))
                .flatMap(filePart -> {
                    final String filename = getFileNameWithoutExtension(filePart.filename());
                    final String uuidResulting = isUUID(filename) ? getUuidFileNameFormatted(filename) : String.format("%s.wav", UUID.randomUUID());
                    return filePart.transferTo(new File(storageDir, uuidResulting));
                }).then();
    }



    @Override
    public void deleteLipsyncArtifacts(String uuid) {
//        checkIfFileExists(uuid);
//        conversionService.convert(uuid);

    }

    @Override
    public Mono<Void> putConversion(String uuid) {
        checkIfFileExists(uuid);

        lipsyncConversionService.convert(uuid);

        return Mono.empty();
    }

    private static void checkIfUuidIsGenuine(String uuid) {
        if(!isUUID(uuid)){
            throw new UuidConversionException(String.format("%s is not a valid UUID and this conversion cannot start", uuid));
        }
    }

    private void checkIfFileExists(String uuid) {
        final String uuidFileNameFormatted = getUuidFileNameFormatted(uuid);
        final File wavFile = new File(storageDir, uuidFileNameFormatted);
        if(!wavFile.exists()){
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
