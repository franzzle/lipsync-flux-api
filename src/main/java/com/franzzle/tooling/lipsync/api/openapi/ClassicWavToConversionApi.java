package com.franzzle.tooling.lipsync.api.openapi;

import com.franzzle.tooling.lipsync.api.model.Convertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class ClassicWavToConversionApi {
    @Value("${wav.storage.dir:#{systemProperties['java.io.tmpdir']}/wavStorageDir}")
    private String storageDir;

    @Autowired
    private Environment environment;

    @PostMapping("/upload")
    public Convertable uploadAudio(@RequestParam("file") MultipartFile file) {

        try {
            final String uuid = UUID.randomUUID().toString().toUpperCase();

            final File wavStorageDir = getWavStorageDir();
            final File dest = new File(wavStorageDir, String.format("%s.wav", uuid));
            file.transferTo(dest);
            return new Convertable(uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getWavStorageDir() {
        final File wavStorageDir = new File(storageDir);
        if (!environment.containsProperty("wav.storage.dir")) {
            System.out.printf("Setting default DIR : %s%n", storageDir);
        }
        if (!wavStorageDir.exists()) {
            boolean mkdirs = wavStorageDir.mkdirs();
            if (!mkdirs) {
                System.out.printf("Could not create directory %s%n", wavStorageDir.getAbsolutePath());
            }
        }
        return wavStorageDir;
    }

}
