package com.franzzle.tooling.lipsync.api;

import com.franzzle.tooling.lipsync.api.model.Convertables;
import com.franzzle.tooling.lipsync.api.service.ConvertedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConvertedApiImpl implements ConvertedApi{
    @Autowired
    private ConvertedService convertedService;

    @Autowired
    private FileUtilities fileUtilities;

    @Override
    public Mono<Convertables> listConverted() {
        return Mono.just(convertedService.listConverted());
    }

    @Override
    public ResponseEntity<Resource> downloadLipsyncFile(String uuidFilename) {
        return getResourceLipsyncResponseEntity(uuidFilename);
    }

    @Override
    public ResponseEntity<Resource> downloadWavsyncFile(String uuidFilename) {
        return getResourceWaveFileResponseEntity(uuidFilename);
    }

    @Override
    public void delete(String uuid) {
        convertedService.deleteResourcesForUuid(uuid);
    }

    private ResponseEntity<Resource> getResourceLipsyncResponseEntity(String uuidFilename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=%s", uuidFilename))
                .body(convertedService.getResultingLipsyncOutput(uuidFilename));
    }

    private ResponseEntity<Resource> getResourceWaveFileResponseEntity(String uuidFilename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=%s", uuidFilename))
                .body(convertedService.getResultingWavInput(uuidFilename));
    }

}
