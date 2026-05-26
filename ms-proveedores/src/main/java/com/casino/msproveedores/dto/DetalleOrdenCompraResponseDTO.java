package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un detalle de orden de compra al cliente
// Evita exponer la entidad JPA directamente
// Se incluye como lista dentro de OrdenCompraResponseDTO
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenCompraResponseDTO {

    // Identificador único del detalle generado por la base de datos
    private Long idDetalle;

    // Nombre del producto comprado
    private String nombreProducto;

    // Cantidad de unidades del producto
    private Integer cantidad;

    // Precio unitario del producto en pesos chilenos
    private Double precioUnitario;

    // Subtotal calculado como cantidad * precioUnitario
    private Double subTotal;
}