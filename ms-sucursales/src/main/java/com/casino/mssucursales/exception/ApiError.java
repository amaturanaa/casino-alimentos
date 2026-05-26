package com.casino.mssucursales.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// DTO estándar para retornar errores de forma estructurada y consistente
// Usado por GlobalExceptionHandler para todas las respuestas de error
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    // Fecha y hora exacta en que ocurrió el error
    private LocalDateTime timestamp;

    // Código HTTP de la respuesta
    // Ejemplo: 400 Bad Request, 404 Not Found, 500 Internal Server Error
    private int status;

    // Categoría del error en texto legible
    // Ejemplo: "Error de validación", "Error de negocio", "Error interno del servidor"
    private String error;

    // Mensaje descriptivo del error para el cliente
    // Ejemplo: "Sede no encontrada", "El empleado ya está asignado a esta sede"
    private String message;

    // Lista de detalles adicionales del error
    // Usado principalmente para errores de validación Bean Validation
    // Puede ser null cuando no hay detalles adicionales
    private List<String> detalles;
}