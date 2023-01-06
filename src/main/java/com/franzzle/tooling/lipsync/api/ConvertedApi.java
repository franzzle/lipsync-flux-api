package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/converted")
@Tag(name = "converted", description = "List converted")
public interface ConvertedApi {
    @Operation(summary = "Get a list of converted", tags = {"converted"})
    @GetMapping
    Mono<Convertables> listConverted();
}
