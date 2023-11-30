package com.franzzle.tooling.lipsync.api.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileUtilities {
    public String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }
}
