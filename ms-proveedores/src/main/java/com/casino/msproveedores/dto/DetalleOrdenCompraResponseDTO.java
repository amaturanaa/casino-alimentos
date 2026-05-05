package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetalleOrdenCompraResponseDTO {
    private Long idDetalle;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;
}
