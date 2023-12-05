package com.franzzle.tooling.lipsync.api.openapi;

import com.franzzle.tooling.lipsync.api.model.Convertable;
import com.franzzle.tooling.lipsync.api.service.LipsyncConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(ConvertableApiImpl.class)
public class ConvertableApiImplTest {

    @Autowired
    private WebTestClient webTestClient;

    private LipsyncConversionService lipsyncConversionService;

    @BeforeEach
    void setUp() {
        lipsyncConversionService = mock(LipsyncConversionService.class);
    }

    @Test
    void testPostFile() throws Exception {
        // Mock any necessary dependencies and setup

        // Create a sample FilePart Mono (you can customize it as needed)
        FilePart filePart = mock(FilePart.class);
        when(filePart.transferTo(Mockito.any(Path.class))).thenReturn(Mono.empty());

        // Mock the behavior of lipsyncConversionService if needed

        // Send a POST request to your controller
        webTestClient.post()
                .uri("/your-api-endpoint")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(filePart)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Convertable.class)
                .isEqualTo(new Convertable("your-uuid")); // Customize expected response as needed
    }

    // Add more test methods as needed for other controller methods

}
