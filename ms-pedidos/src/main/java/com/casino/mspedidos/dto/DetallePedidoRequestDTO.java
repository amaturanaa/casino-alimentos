package com.casino.mspedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "El id del plato es obligatorio")
    private Long platoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "El valor no puede ser cero, ni negativo")
    private Integer cantidad;

    @NotNull @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Double subTotal;
}
