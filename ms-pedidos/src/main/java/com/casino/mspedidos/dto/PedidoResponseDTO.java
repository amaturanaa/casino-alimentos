package com.casino.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    private Long idPedido;
    private Long usuarioId;
    private Long sedeId;
    private LocalDateTime fechaHora;
    private String estado;
    private Double totalPedido;
    private List<DetallePedidoResponseDTO> detalles;
}
