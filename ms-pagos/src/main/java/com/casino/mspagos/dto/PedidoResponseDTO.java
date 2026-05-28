package com.casino.mspagos.dto;

import lombok.Data;

@Data
public class PedidoResponseDTO {
    private Long idPedido;
    private Long usuarioId;
    private Long sedeId;
    private String estado;
    private Double totalPedido;
}