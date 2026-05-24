package com.casino.msinventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear un tipo de movimiento de stock
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Los tipos de movimiento clasifican los movimientos del inventario
@Data
public class TipoMovimientoRequestDTO {

    // Nombre descriptivo del tipo de movimiento
    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "ENTRADA", "SALIDA", "MERMA"
    @NotBlank(message = "El nombre del tipo de movimiento es obligatorio")
    @Size(max = 50, message = "El nombre del tipo no puede superar los 50 caracteres")
    private String nombreTipoMovimiento;
}