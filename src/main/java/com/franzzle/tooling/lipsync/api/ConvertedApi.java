package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/converted")
@Tag(name = "converted", description = "API to handle and manage results of the converted resources")
public interface ConvertedApi {
    @Operation(summary = "Get a list of converted", tags = {"converted"})
    @GetMapping
    Mono<Convertables> listConverted();

    //TODO Maybe one controller and get the accepted
    @Operation(summary = "Download Lipsync File", tags = {"converted"})
    @GetMapping(path = "/lipsync/{uuidFilename:.+}")
    ResponseEntity<Resource> downloadLipsyncFile(@PathVariable String uuidFilename);

    @Operation(summary = "Download WAV File", tags = {"converted"})
    @GetMapping(path = "/wav/{uuidFilename:.+}")
    ResponseEntity<Resource> downloadWavsyncFile(@PathVariable String uuidFilename);

    @Operation(summary = "Remove WAV and json File", tags = {"converted"})
    @DeleteMapping(path = "/{uuid}")
    void delete(@PathVariable String uuid);

}
