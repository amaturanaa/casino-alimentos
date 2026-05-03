package com.casino.mspedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long sedeId;

    @NotNull
    private List<DetallePedidoRequestDTO> detalles;
}
