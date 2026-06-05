package com.casino.msusuarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Clase de configuración de la documentación OpenAPI/Swagger
// @Configuration indica que esta clase define beans de Spring
@Configuration
public class OpenApiConfig {

    // Bean que personaliza el título, versión y descripción de la documentación
    // Aparece en la parte superior de la página de Swagger UI
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API ms-usuarios")
                        .version("1.0")
                        .description("Microservicio de gestión de usuarios del casino"));
    }
}