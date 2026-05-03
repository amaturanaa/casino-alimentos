package com.casino.mspedidos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoResponseDTO {

    private Long idDetallePedido;

    private Long platoId;

    private Integer cantidad;

    private Double subTotal;
}
