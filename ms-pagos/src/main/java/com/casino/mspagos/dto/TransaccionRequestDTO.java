package com.casino.mspagos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransaccionRequestDTO {

    @NotNull
    private Long pedidoId;

    @NotNull
    private Long usuarioId;

    @NotNull
    @Min(0)
    private Double monto;

    @NotBlank
    private String metodoPago;
}
