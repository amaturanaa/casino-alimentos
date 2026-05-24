package com.casino.msmenu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear un tipo de plato
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Los tipos de plato clasifican los platos del menú del casino
@Data
public class TipoPlatoRequestDTO {

    // Nombre descriptivo del tipo de plato
    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
    @NotBlank(message = "El nombre del tipo de plato es obligatorio")
    @Size(max = 50, message = "El nombre del tipo de plato no puede superar los 50 caracteres")
    private String nombreTipoPlato;
}