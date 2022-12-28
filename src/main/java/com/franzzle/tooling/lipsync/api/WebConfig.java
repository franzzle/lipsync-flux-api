package com.franzzle.tooling.lipsync.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebExceptionHandler;

@Configuration
public class WebConfig {
    @Bean
    public WebExceptionHandler customWebExceptionHandler() {
        return new ConversionWebExceptionHandler();
    }
}
