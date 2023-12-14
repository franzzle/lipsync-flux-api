package com.franzzle.tooling.lipsync.api.openapi;

import com.franzzle.tooling.lipsync.api.service.ConvertedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SpeechRecognitionApiImpl {
    private static final Logger log = LoggerFactory.getLogger(SpeechRecognitionApiImpl.class);
    @Autowired
    private ConvertedService convertedService;
    @Value("${speech.recognition.api.url}")
    private String speechRecognitionApiUrl;

    public ResponseEntity<String> transcribeAudio(String uuid) {
        try {
            Resource audioResource = convertedService.getResultingWavInput(String.format("%s.wav", uuid));

            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            bodyBuilder.part("audio_file", audioResource)
                    .header("Content-Type", "audio/x-wav");

            RestTemplate restTemplate = new RestTemplate();

            // Make a POST request to the external API
            ResponseEntity<String> response = restTemplate.postForEntity(
                    speechRecognitionApiUrl + "?task=transcribe&language=en&encode=true&output=txt",
                    bodyBuilder.build(),
                    String.class
            );

             log.info(response.getBody());

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing the request.");
        }
    }
}
