package com.casino.msinventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para registrar un movimiento de stock
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Un movimiento actualiza automáticamente el stock del ingrediente afectado
@Data
public class MovimientoStockRequestDTO {

    // Identificador del ingrediente afectado por el movimiento
    // @NotNull valida que no sea nulo
    // El Service verifica que el ingrediente exista antes de registrar
    @NotNull
    private Long ingredienteId;

    // Identificador del tipo de movimiento
    // @NotNull valida que no sea nulo
    // Ejemplo: 1=ENTRADA, 2=SALIDA, 3=MERMA
    @NotNull
    private Long tipoMovimientoId;

    // Cantidad de unidades involucradas en el movimiento
    // @Min valida que el valor sea mayor o igual a 0
    // ENTRADA: suma al stock, SALIDA y MERMA: resta del stock
    @NotNull(message = "La cantidad es obligatorio")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Double cantidad;

    // Descripción del motivo del movimiento
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // Ejemplo: "Recepción proveedor", "Consumo cocina", "Producto vencido"
    @NotBlank(message = "El motivo del movimiento es obligatorio")
    private String motivo;
}