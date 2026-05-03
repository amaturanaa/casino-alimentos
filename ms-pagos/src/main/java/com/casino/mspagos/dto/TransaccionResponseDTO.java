package com.casino.mspagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionResponseDTO {

    private Long idTransaccion;
    private Long pedidoId;
    private Long usuarioId;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String estadoPago;
}
