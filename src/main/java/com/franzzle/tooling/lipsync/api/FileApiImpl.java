package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.service.RhubarbService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/conversion")
@Tag(name = "conversion", description = "conversion to lipsync wav")
public class FileApiImpl implements FileApi {

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Autowired
    private RhubarbService rhubarbService;

    public Mono<Void> postFile(Mono<FilePart>  filePartMono) {
        return filePartMono
                .doOnNext(filePart -> System.out.println(filePart.filename()))
                .flatMap(filePart -> {
                    final String filename = getFileNameWithoutExtension(filePart.filename());
                    final String uuidResulting = isUUID(filename) ? getUuidFileNameFormatted(filename) : String.format("%s.wav", UUID.randomUUID());
                    return filePart.transferTo(new File(storageDir, uuidResulting));
                }).then();
    }



    @Override
    public void deleteLipsyncArtifacts(String uuid) {
        System.out.println(uuid);
    }

    @Override
    public Mono<Void> putConversion(String uuid) {
        if(!isUUID(uuid)){
            throw new UuidConversionException(String.format("%s is not a valid UUID and this conversion cannot start", uuid));
        }

        return null;
    }

    public static boolean isUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
