package com.casino.msusuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear o actualizar un usuario
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// La contraseña se encripta con BCrypt en el Service antes de persistir
@Data
public class UsuarioRequestDTO {

    // RUT del usuario — identificador único nacional
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // @Size limita la longitud máxima del campo
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede superar 12 caracteres")
    private String rut_usuario;

    // Primer nombre del usuario — obligatorio
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 50)
    private String pnombre_usuario;

    // Segundo nombre del usuario — opcional
    @Size(max = 50)
    private String snombre_usuario;

    // Apellido paterno del usuario — obligatorio
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 50)
    private String appaterno_usuario;

    // Apellido materno del usuario — opcional
    @Size(max = 50)
    private String apmaterno_usuario;

    // Email del usuario — credencial única de acceso al sistema
    // @Email valida que tenga formato de correo electrónico válido
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    private String email;

    // Contraseña en texto plano — se encripta con BCrypt en el Service
    // @Size valida longitud mínima de seguridad
    // La contraseña NO se retorna en las respuestas (no está en UsuarioResponseDTO)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    // Identificador del rol asignado al usuario
    // @NotNull valida que no sea nulo
    // Referencia local a la entidad Rol dentro del mismo microservicio
    @NotNull(message = "El rol es obligatorio")
    private Long rol_id;
}