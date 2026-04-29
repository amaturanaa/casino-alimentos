package com.casino.msusuarios.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String nombre_rol;

    @Size(max = 100, message = "La descripción no puede superar 100 caracteres")
    private String descripcion;
}
