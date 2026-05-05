package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrdenCompraResponseDTO {
    private Long idOrdenCompra;
    private Long proveedorId;
    private String razonSocial;
    private Long sedeId;
    private LocalDateTime fechaSolicitud;
    private String estado;
    private Double costoTotal;
    private List<DetalleOrdenCompraResponseDTO> detalles;
}
