package com.franzzle.tooling.lipsync.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
@AutoConfigureWebTestClient
public class WebConfigTest {

    @Autowired
    private RouterFunction<ServerResponse> routerFunction;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Test if the route serves html")
    @Test
    public void testRoute() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML);
    }
}

