package com.franzzle.tooling.lipsync.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "conversion", description = "File upload and management")
public interface FileApi {
    /**
     *
     * @param file
     * @return
     */
    @Operation(summary = "Upload a .wav file", tags = {"conversion"})
    @PostMapping
    Mono<Void> postFile(@RequestPart("file") Mono<FilePart> file);

    /**
     *
     * @param uuid
     */
    @Operation(summary = "Remove lipsync artifacts, wav and json", tags = {"conversion"})
    @DeleteMapping(path = "/{uuid}")
    void deleteLipsyncArtifacts(@PathVariable String uuid);

    /**
     *
     * @param uuid
     * @return
     */
    @PutMapping(path = "/{uuid}")
    Mono<Void> putConversion(@PathVariable String uuid);
}
