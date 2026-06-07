package com.casino.mssucursales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de OpenAPI/Swagger para el microservicio
// @Configuration marca esta clase como fuente de beans de Spring
// El bean OpenAPI personaliza el título, versión y descripción de la documentación
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API ms-sucursales")
                        .version("1.0")
                        .description("Microservicio para la gestión de sedes del casino de alimentos"));
    }
}