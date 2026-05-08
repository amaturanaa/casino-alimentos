package com.casino.msinventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoStockRequestDTO {

    @NotNull
    private Long ingredienteId;

    @NotNull
    private Long tipoMovimientoId;

    @NotNull(message = "La cantidad es obligatorio")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Double cantidad;

    @NotBlank(message = "El motivo del movimiento es obligatorio")
    private String motivo;
}
