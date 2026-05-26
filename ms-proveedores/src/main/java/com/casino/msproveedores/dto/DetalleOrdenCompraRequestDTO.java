package com.casino.msproveedores.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para cada detalle de una orden de compra
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Un detalle representa un producto dentro de la orden de compra
@Data
public class DetalleOrdenCompraRequestDTO {

    // Nombre del producto a comprar
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    private String nombreProducto;

    // Cantidad de unidades del producto
    // @NotNull valida que no sea nulo
    // @Min valida que la cantidad sea al menos 1
    @NotNull
    @Min(1)
    private Integer cantidad;

    // Precio unitario del producto en pesos chilenos
    // @NotNull valida que no sea nulo
    // @Min valida que el precio no sea negativo
    @NotNull
    @Min(0)
    private Double precioUnitario;
}