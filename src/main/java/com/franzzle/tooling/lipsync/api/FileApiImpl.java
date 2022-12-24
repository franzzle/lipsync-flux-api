package com.franzzle.tooling.lipsync.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileApiImpl implements FileApi {

    @PostMapping
    public FileMetadata createFile(@RequestParam("file") MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        // Do something with the uploaded file, such as saving it to a database or local filesystem
        return new FileMetadata(fileId);
    }
}
