package com.casino.msreservas.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-usuarios via Feign Client
// Es una copia simplificada del DTO de ms-usuarios
// Solo contiene los campos necesarios para la validación de usuario
// Cada microservicio que consume otro debe tener su propio DTO local
// Los campos usan snake_case para coincidir con la respuesta JSON de ms-usuarios
@Data
public class UsuarioResponseDTO {

    // Identificador único del usuario en ms-usuarios
    // Usa snake_case (id_usuario) para coincidir con el JSON de ms-usuarios
    private Long id_usuario;

    // Email del usuario — usado en logs para identificar al usuario verificado
    private String email;

    // Estado del usuario en el sistema
    // true = activo (puede realizar reservas)
    // false = inactivo (el Service rechaza crear reservas para este usuario)
    private Boolean activo;
}