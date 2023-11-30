package com.franzzle.tooling.lipsync.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "transcribing", description = "Handles transcribing")
@RequestMapping("/asr")
public interface SpeechRecognitionApi {
    @Operation(summary = "Transcribe", tags = {"transcribing"})
    @PostMapping("/transcribe/{uuid}")
    ResponseEntity<String> transcribeAudio(@PathVariable String uuid);
}
