package com.casino.msproveedores.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetalleOrdenCompraRequestDTO {
    @NotBlank
    private String nombreProducto;

    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull @Min(0)
    private Double precioUnitario;
}

