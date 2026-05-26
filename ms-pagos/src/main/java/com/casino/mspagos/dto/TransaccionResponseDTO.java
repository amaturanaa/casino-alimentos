package com.casino.mspagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// DTO de salida para retornar datos de una transacción al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionResponseDTO {

    // Identificador único de la transacción generado por la base de datos
    private Long idTransaccion;

    // Identificador del pedido asociado al pago
    // Referencia a ms-pedidos sin FK física entre bases de datos
    private Long pedidoId;

    // Identificador del usuario que realizó el pago
    // Referencia a ms-usuarios sin FK física entre bases de datos
    private Long usuarioId;

    // Monto total pagado en pesos chilenos
    private Double monto;

    // Método de pago utilizado
    // Ejemplo: "TARJETA_CREDITO", "JUNAEB", "SUBSIDIO_EMPRESA", "EFECTIVO"
    private String metodoPago;

    // Fecha y hora exacta en que se procesó el pago
    // LocalDateTime almacena fecha y hora sin zona horaria
    private LocalDateTime fechaPago;

    // Estado actual de la transacción
    // Ejemplo: "PENDIENTE", "APROBADO", "RECHAZADO"
    private String estadoPago;
}