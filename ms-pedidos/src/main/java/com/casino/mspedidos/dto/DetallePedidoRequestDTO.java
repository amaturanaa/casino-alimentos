package com.casino.mspedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallePedidoRequestDTO {

    @NotNull
    private Long platoId;

    @NotNull @Min(1)
    private Integer cantidad;

    @NotNull @Min(0)
    private Double subTotal;
}
