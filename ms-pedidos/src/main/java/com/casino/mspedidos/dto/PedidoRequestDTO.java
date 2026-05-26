package com.casino.mspedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

// DTO de entrada para crear un pedido con sus detalles
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Un pedido puede contener múltiples detalles (uno por cada plato)
@Data
public class PedidoRequestDTO {

    // Identificador del usuario que realiza el pedido
    // @NotNull valida que no sea nulo
    // Referencia a ms-usuarios sin FK física entre bases de datos
    @NotNull
    private Long usuarioId;

    // Identificador de la sede donde se realiza el pedido
    // @NotNull valida que no sea nulo
    // Referencia a ms-sucursales sin FK física entre bases de datos
    @NotNull
    private Long sedeId;

    // Lista de detalles del pedido — uno por cada plato solicitado
    // @NotNull valida que la lista no sea nula
    // Cada detalle contiene platoId, cantidad y subTotal
    @NotNull
    private List<DetallePedidoRequestDTO> detalles;
}