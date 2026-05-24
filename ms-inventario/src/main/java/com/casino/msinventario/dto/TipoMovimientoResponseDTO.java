package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un tipo de movimiento al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMovimientoResponseDTO {

    // Identificador único del tipo de movimiento generado por la base de datos
    private Long idTipoMovimiento;

    // Nombre descriptivo del tipo de movimiento
    // Ejemplo: "ENTRADA", "SALIDA", "MERMA"
    // Usado en MovimientoStockResponseDTO para mostrar el nombre en vez del id
    private String nombreTipoMovimiento;
}