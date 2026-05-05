package com.casino.msproveedores.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrdenCompraRequestDTO {
    @NotNull
    private Long proveedorId;

    @NotNull
    private Long sedeId;

    @NotNull
    private List<DetalleOrdenCompraRequestDTO> detalles;
}
