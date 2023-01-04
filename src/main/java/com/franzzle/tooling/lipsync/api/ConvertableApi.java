package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequestMapping("/conversion")
@Tag(name = "convertable", description = "File upload and management")
public interface ConvertableApi {
    /**
     * @param file
     * @return
     */
    @Operation(summary = "Upload a .wav file", tags = {"convertable"})
    @PostMapping
    Mono<Convertable> postFile(@RequestPart("file") Mono<FilePart> file);

    /**
     * @param uuid
     */
    @Operation(summary = "Remove lipsync artifacts, wav and json", tags = {"convertable"})
    @DeleteMapping(path = "/{uuid}")
    void deleteLipsyncArtifacts(@Valid @ValidUuid @PathVariable String uuid);

    /**
     * @param uuid
     * @return
     */
    @Operation(summary = "Start the conversion", tags = {"convertable"})
    @PutMapping(path = "/{uuid}")
    Convertable putConversion(@Valid @ValidUuid @PathVariable String uuid);

    @Operation(summary = "Get a list of convertables", tags = {"convertable"})
    @GetMapping
    Mono<Convertables> list();

}
