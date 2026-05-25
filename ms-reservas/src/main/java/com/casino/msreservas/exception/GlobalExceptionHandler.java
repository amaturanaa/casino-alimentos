package com.casino.msreservas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Manejador global de excepciones para todos los controllers del microservicio
// @RestControllerAdvice intercepta excepciones lanzadas por cualquier controller
// Centraliza el manejo de errores evitando try/catch repetido en cada controller
// Garantiza respuestas de error consistentes con ResponseEntity y códigos HTTP correctos
// @Slf4j genera automáticamente el logger mediante Lombok
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación Bean Validation (@Valid en controllers)
    // Se activa cuando un campo del DTO no cumple las anotaciones de validación
    // Retorna 400 Bad Request con lista detallada de campos inválidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        log.warn("Error de validación: {}", ex.getMessage());

        // Recorre todos los errores de campo y los agrega a la lista de detalles
        List<String> detalles = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            detalles.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                "Uno o más campos son inválidos",
                detalles
        );

        return ResponseEntity.badRequest().body(error);
    }

    // Maneja errores de lógica de negocio lanzados desde el Service
    // Ejemplo: usuario inactivo, sede no operativa, sin cupos disponibles
    // Retorna 400 Bad Request con mensaje descriptivo del error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        log.error("Error de negocio: {}", ex.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de negocio",
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(error);
    }

    // Maneja errores de recurso no encontrado en la base de datos
    // Se activa cuando JPA lanza EntityNotFoundException
    // Retorna 404 Not Found con mensaje descriptivo
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            jakarta.persistence.EntityNotFoundException ex) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Maneja cualquier error inesperado no capturado por los handlers anteriores
    // Retorna 500 Internal Server Error ocultando detalles internos al cliente
    // El log registra el error completo para diagnóstico interno
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                null
        );

        return ResponseEntity.internalServerError().body(error);
    }
}