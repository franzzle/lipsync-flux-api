package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.model.Convertable;
import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.FileUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class ConvertedServiceImpl implements ConvertedService{
    public static final String DEST_LIPSYNC = "destLipsync";

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Autowired
    private FileUtilities fileUtilities;

    @Override
    public Convertables listConverted() {
        final List<String> filenames =
                Arrays.stream(Objects.requireNonNull(new File(storageDir, DEST_LIPSYNC)
                                .list((dir, name) -> name.endsWith("json"))))
                        .toList();

        final Convertables convertables = new Convertables();
        convertables.setConvertables(new ArrayList<>());

        for (String uuidFileName : filenames) {
            final String uuidFromFile = fileUtilities.getFileNameWithoutExtension(uuidFileName);
            Convertable convertable = new Convertable(uuidFromFile);
            convertables.getConvertables().add(convertable);
        }
        return convertables;
    }
}
