package com.franzzle.tooling.lipsync.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableOpenApi
public class LipsyncApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LipsyncApiApplication.class, args);
    }
}
