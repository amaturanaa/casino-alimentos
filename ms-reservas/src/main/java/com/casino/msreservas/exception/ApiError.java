package com.casino.msreservas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// DTO estándar para retornar errores de forma estructurada y consistente
// Usado por GlobalExceptionHandler para todas las respuestas de error
// Garantiza que el cliente siempre reciba el mismo formato de error
// independientemente del tipo de excepción que ocurra
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    // Fecha y hora exacta en que ocurrió el error
    // Útil para correlacionar errores con logs del servidor
    private LocalDateTime timestamp;

    // Código HTTP de la respuesta
    // Ejemplo: 400 Bad Request, 404 Not Found, 500 Internal Server Error
    private int status;

    // Categoría del error en texto legible
    // Ejemplo: "Error de validación", "Error de negocio", "Error interno del servidor"
    private String error;

    // Mensaje descriptivo del error para el cliente
    // Ejemplo: "Reserva no encontrada", "No hay cupos disponibles en este turno"
    private String message;

    // Lista de detalles adicionales del error
    // Usado principalmente para errores de validación Bean Validation
    // Ejemplo: ["usuarioId: must not be null", "turnoId: must not be null"]
    // Puede ser null cuando no hay detalles adicionales
    private List<String> detalles;
}