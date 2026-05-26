package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// DTO de salida para retornar datos de una orden de compra al cliente
// Evita exponer la entidad JPA directamente
// Incluye datos del proveedor y lista de detalles para respuesta completa
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraResponseDTO {

    // Identificador único de la orden generado por la base de datos
    private Long idOrdenCompra;

    // Identificador del proveedor asociado a la orden
    private Long proveedorId;

    // Razón social del proveedor — obtenido desde la entidad Proveedor
    // Evita que el cliente tenga que hacer una segunda consulta
    private String razonSocial;

    // Identificador de la sede que realizó la compra
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Fecha y hora exacta en que se creó la orden de compra
    // LocalDateTime almacena fecha y hora sin zona horaria
    private LocalDateTime fechaSolicitud;

    // Estado actual de la orden de compra
    // Valores válidos: PENDIENTE, RECIBIDA, CANCELADA
    private String estado;

    // Costo total de la orden en pesos chilenos
    // Calculado como la suma de subtotales de todos los detalles
    private Double costoTotal;

    // Lista de detalles de la orden — uno por cada producto
    private List<DetalleOrdenCompraResponseDTO> detalles;
}