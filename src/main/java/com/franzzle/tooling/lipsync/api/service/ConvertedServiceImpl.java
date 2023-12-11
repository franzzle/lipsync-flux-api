package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.model.Convertable;
import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.util.FileUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ConvertedServiceImpl implements ConvertedService {
    @Value("${lipsync.storage.dir:#{systemProperties['java.io.tmpdir']}/jsonStorageDir}")
    private String jsonOutputDir;

    @Value("${wav.storage.dir:#{systemProperties['java.io.tmpdir']}/wavStorageDir}")
    private String wavInputDir;


    @Autowired
    private Environment environment;

    @Autowired
    private FileUtilities fileUtilities;

    @Override
    public Convertables listConverted() {
        final File jsonStorageDir = new File(jsonOutputDir);
        if(!environment.containsProperty("lipsync.storage.dir")){
            if(!jsonStorageDir.exists()) {
                jsonStorageDir.mkdirs();
            }
            System.out.println(String.format("Setting default DIR : %s", jsonStorageDir));
        }

        final List<String> filenames =
                Arrays.stream(Objects.requireNonNull(jsonStorageDir
                                .list((dir, name) -> name.endsWith("json"))))
                        .collect(Collectors.toList());


        final Convertables convertables = new Convertables();
        convertables.setConvertables(new ArrayList<>());

        for (String uuidFileName : filenames) {
            final String uuidFromFile = fileUtilities.getFileNameWithoutExtension(uuidFileName);
            Convertable convertable = Convertable.fromUuid(uuidFromFile);
            convertables.getConvertables().add(convertable);
        }
        return convertables;
    }

    @Override
    public Resource getResultingLipSyncOutput(String uuid) {
        try {
            File file = new File(new File(jsonOutputDir), uuid);
            URL fileUrl = file.toURI().toURL();
            return new UrlResource(fileUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Resource getResultingWavInput(String uuid) {
        try {
            File file = new File(new File(wavInputDir), uuid);
            URL fileUrl = file.toURI().toURL();
            return new UrlResource(fileUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteResourcesForUuid(String uuid) {
        final File jsonFile = new File(new File(jsonOutputDir), String.format("%s.json", uuid));
        if(jsonFile.exists()){
            jsonFile.delete();
        }
        final File wavFile = new File(wavInputDir, String.format("%s.wav", uuid));
        if(wavFile.exists()){
            wavFile.delete();
        }
    }
}
