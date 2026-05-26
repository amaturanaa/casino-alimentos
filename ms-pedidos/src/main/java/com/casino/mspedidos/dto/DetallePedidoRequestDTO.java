package com.casino.mspedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para cada detalle de un pedido
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Un pedido puede tener múltiples detalles (uno por cada plato)
@Data
public class DetallePedidoRequestDTO {

    // Identificador del plato solicitado en este detalle
    // @NotNull valida que no sea nulo
    // Referencia a ms-menu sin FK física entre bases de datos
    @NotNull(message = "El id del plato es obligatorio")
    private Long platoId;

    // Cantidad de unidades del plato solicitadas
    // @NotNull valida que no sea nulo
    // @Min valida que la cantidad sea al menos 1
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "El valor no puede ser cero, ni negativo")
    private Integer cantidad;

    // Subtotal calculado para este detalle (precio unitario * cantidad)
    // @NotNull valida que no sea nulo
    // @Min valida que el subtotal no sea negativo
    @NotNull
    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Double subTotal;
}