package com.franzzle.tooling.lipsync.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.core.io.ResourceLoader;


@Configuration
public class WebConfig {
    private final ResourceLoader resourceLoader;

    public WebConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(RequestPredicates.GET("/"), request ->
                ServerResponse.ok().contentType(MediaType.TEXT_HTML)
                        .syncBody(resourceLoader.getResource("classpath:/static/index.html")));
    }

}
