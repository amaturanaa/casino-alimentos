package com.casino.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// DTO de salida para retornar datos de un pedido al cliente
// Evita exponer la entidad JPA directamente
// Incluye la lista de detalles para retornar el pedido completo en una sola respuesta
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    // Identificador único del pedido generado por la base de datos
    private Long idPedido;

    // Identificador del usuario que realizó el pedido
    // Referencia a ms-usuarios sin FK física entre bases de datos
    private Long usuarioId;

    // Identificador de la sede donde se realizó el pedido
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Fecha y hora exacta en que se creó el pedido
    // LocalDateTime almacena fecha y hora sin zona horaria
    private LocalDateTime fechaHora;

    // Estado actual del pedido
    // Flujo: RECIBIDO → EN_PREPARACION → LISTO_RETIRO → ENTREGADO
    private String estado;

    // Total del pedido en pesos chilenos
    // Calculado como la suma de todos los subtotales de los detalles
    private Double totalPedido;

    // Lista de detalles del pedido — uno por cada plato solicitado
    // Incluida para retornar el pedido completo sin consultas adicionales
    private List<DetallePedidoResponseDTO> detalles;
}