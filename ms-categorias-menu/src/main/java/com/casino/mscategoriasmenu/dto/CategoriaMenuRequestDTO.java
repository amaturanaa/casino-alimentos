package com.casino.mscategoriasmenu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear o actualizar una categoría de menú
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class CategoriaMenuRequestDTO {

    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "Almuerzo Completo", "Ensaladas", "Postres"
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    // Estado de la categoría en el sistema
    // @NotNull valida que no sea nulo (para tipos no String como Boolean)
    // true = activa (ms-menu puede crear platos con esta categoría)
    // false = inactiva (ms-menu rechaza platos con esta categoría via Feign)
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}