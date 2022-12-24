package com.franzzle.tooling.lipsync.api;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileApi {

    FileMetadata createFile(@RequestParam("file") MultipartFile file);
}
