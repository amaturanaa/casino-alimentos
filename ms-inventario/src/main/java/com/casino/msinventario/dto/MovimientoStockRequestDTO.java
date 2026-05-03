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

    @NotNull @Min(0)
    private Double cantidad;

    @NotBlank
    private String motivo;
}
