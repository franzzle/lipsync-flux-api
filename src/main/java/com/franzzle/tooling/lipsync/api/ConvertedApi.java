package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/converted")
@Tag(name = "converted", description = "List converted")
public interface ConvertedApi {
    @Operation(summary = "Get a list of converted", tags = {"converted"})
    @GetMapping
    Mono<Convertables> listConverted();

    @Operation(summary = "Download Lipsync File", tags = {"converted"})
    @GetMapping(path = "/lipsync/{uuidFilename:.+}")
    ResponseEntity<Resource> downloadLipsyncFile(@PathVariable String uuidFilename);
}
