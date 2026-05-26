package com.casino.msusuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear un rol del sistema
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Controller
@Data
public class RolRequestDTO {

    // Nombre único del rol en el sistema
    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo
    // Ejemplo: ROLE_ADMIN, ROLE_OPERADOR, ROLE_CLIENTE
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String nombre_rol;

    // Descripción del rol — campo opcional
    // @Size limita la longitud máxima del campo
    // Ejemplo: "Administrador del sistema", "Cliente del casino"
    @Size(max = 100, message = "La descripción no puede superar 100 caracteres")
    private String descripcion;
}