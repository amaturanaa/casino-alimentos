package com.casino.msusuarios.dto;

import lombok.Data;

// DTO de salida para retornar datos de un usuario al cliente
// Evita exponer la entidad JPA directamente
// IMPORTANTE: no incluye la contraseña por seguridad
// Este DTO es replicado localmente en ms-reservas para deserializar
// la respuesta Feign sin crear dependencia entre microservicios
@Data
public class UsuarioResponseDTO {

    // Identificador único del usuario generado por la base de datos
    // Usa snake_case para coincidir con el JSON esperado por ms-reservas via Feign
    private Long id_usuario;

    // RUT del usuario sin dígito verificador
    private String rut_usuario;

    // Primer nombre del usuario
    private String pnombre_usuario;

    // Segundo nombre del usuario — puede ser null
    private String snombre_usuario;

    // Apellido paterno del usuario
    private String appaterno_usuario;

    // Apellido materno del usuario — puede ser null
    private String apmaterno_usuario;

    // Email del usuario — credencial única del sistema
    private String email;

    // Estado del usuario en el sistema
    // true = activo (puede usar el sistema)
    // false = inactivo (baja lógica, no se elimina el registro)
    // Verificado por ms-reservas via Feign antes de crear una reserva
    private Boolean activo;

    // Nombre del rol asignado al usuario
    // Ejemplo: ROLE_ADMIN, ROLE_OPERADOR, ROLE_CLIENTE
    private String nombre_rol;
}