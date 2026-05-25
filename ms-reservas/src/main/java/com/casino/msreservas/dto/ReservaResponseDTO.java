package com.casino.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

// DTO de salida para retornar datos de una reserva al cliente
// Evita exponer la entidad JPA directamente
// Incluye datos del turno para enriquecer la respuesta sin consultas adicionales
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO {

    // Identificador único de la reserva generado por la base de datos
    private Long idReserva;

    // Identificador del usuario que realizó la reserva
    // Referencia a ms-usuarios sin FK física entre bases de datos
    private Long usuarioId;

    // Identificador del turno reservado
    private Long turnoId;

    // Identificador de la sede donde se realiza la reserva
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Fecha y hora exacta en que se creó la reserva
    // LocalDateTime almacena fecha y hora sin zona horaria
    private LocalDateTime fechaCreacion;

    // Estado actual de la reserva
    // Ejemplo: "ACTIVA", "CANCELADA"
    private String estado;

    // Hora de inicio del turno reservado — obtenido desde la entidad TurnoDisponible
    // Evita que el cliente tenga que hacer una segunda consulta para obtener la hora
    private LocalTime horaInicio;

    // Hora de fin del turno reservado — obtenido desde la entidad TurnoDisponible
    private LocalTime horaFin;
}