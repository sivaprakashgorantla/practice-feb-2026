package com.java8.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Practice Feb 2026 - Student API")
                        .version("1.0")
                        .description("CRUD API for Student entities (H2 in-memory DB). Swagger UI available at /swagger-ui/index.html)"));
    }
}

