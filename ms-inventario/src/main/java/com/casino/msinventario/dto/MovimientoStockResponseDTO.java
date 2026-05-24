package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// DTO de salida para retornar datos de un movimiento de stock al cliente
// Evita exponer la entidad JPA directamente
// Incluye datos del ingrediente y tipo de movimiento para enriquecer la respuesta
// Evita que el cliente tenga que hacer consultas adicionales
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoStockResponseDTO {

    // Identificador único del movimiento generado por la base de datos
    private Long idMovimiento;

    // Identificador del ingrediente afectado por el movimiento
    private Long ingredienteId;

    // Nombre del ingrediente afectado — obtenido desde la entidad Ingrediente
    // Evita que el cliente tenga que hacer una segunda consulta para obtener el nombre
    private String nombreIngrediente;

    // Nombre del tipo de movimiento — obtenido desde la entidad TipoMovimiento
    // Ejemplo: "ENTRADA", "SALIDA", "MERMA"
    private String tipoMovimiento;

    // Cantidad de unidades involucradas en el movimiento
    private Double cantidad;

    // Fecha y hora exacta en que se registró el movimiento
    // LocalDateTime almacena fecha y hora sin zona horaria
    private LocalDateTime fechaMovimiento;

    // Descripción del motivo del movimiento
    // Ejemplo: "Recepción proveedor", "Consumo cocina", "Producto vencido"
    private String motivo;

    // Stock resultante del ingrediente después de aplicar el movimiento
    // Calculado en el Service: ENTRADA suma, SALIDA y MERMA restan
    private Double stockResultante;
}