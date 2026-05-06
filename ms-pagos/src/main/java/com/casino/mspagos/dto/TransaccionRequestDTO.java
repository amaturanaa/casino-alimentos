package com.casino.mspagos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransaccionRequestDTO {

    @NotNull(message = "El id del pedido es obligatorio")
    private Long pedidoId;

    @NotNull(message = "El id usuario es obligatorio")
    private Long usuarioId;

    @NotNull
    @Min(value = 0, message = "El monto no puede ser negativo")
    private Double monto;

    @NotBlank(message = "Metodo de pago es obligatorio")
    private String metodoPago;
}
