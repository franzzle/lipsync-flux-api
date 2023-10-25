package com.franzzle.tooling.lipsync.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "transcribing", description = "Handles transcribing")
public interface SpeechRecognitionApi {
    @Operation(summary = "Transcribe", tags = {"transcribing"})
    @PostMapping("/transcribe")
    ResponseEntity<String> transcribeAudio(@RequestParam("uuid") String uuid);
}
