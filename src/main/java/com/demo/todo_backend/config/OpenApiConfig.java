package com.demo.todo_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI todoApi() {
        return new OpenAPI()
                       .info(new Info()
                                     .title("Todo API Documentation")
                                     .description("API endpoints for Task Management System")
                                     .version("1.0.0"));
    }
}

