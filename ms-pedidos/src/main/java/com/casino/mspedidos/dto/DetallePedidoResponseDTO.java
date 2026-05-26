package com.casino.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un detalle de pedido al cliente
// Evita exponer la entidad JPA directamente
// Se incluye como lista dentro de PedidoResponseDTO
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoResponseDTO {

    // Identificador único del detalle generado por la base de datos
    private Long idDetallePedido;

    // Identificador del plato solicitado
    // Referencia a ms-menu sin FK física entre bases de datos
    private Long platoId;

    // Cantidad de unidades del plato solicitadas
    private Integer cantidad;

    // Subtotal de este detalle en pesos chilenos
    // Calculado como precio unitario * cantidad
    private Double subTotal;
}