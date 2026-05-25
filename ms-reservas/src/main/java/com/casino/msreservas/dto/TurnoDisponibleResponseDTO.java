package com.casino.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

// DTO de salida para retornar datos de un turno disponible al cliente
// Evita exponer la entidad JPA directamente
// Incluye el campo disponible calculado para facilitar el filtrado en el cliente
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDisponibleResponseDTO {

    // Identificador único del turno generado por la base de datos
    private Long idTurno;

    // Identificador de la sede donde se realiza el turno
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    private LocalDate fecha;

    // Hora de inicio del turno en formato HH:mm:ss
    private LocalTime horaInicio;

    // Hora de fin del turno en formato HH:mm:ss
    private LocalTime horaFin;

    // Capacidad máxima de comensales para este turno
    private Integer capacidad;

    // Cupos restantes disponibles para reservar
    // Se decrementa cada vez que se crea una reserva en este turno
    // Se incrementa cuando se cancela una reserva
    private Integer cuposRestantes;

    // Indicador calculado en el Service
    // true = hay cupos disponibles (cuposRestantes > 0)
    // false = turno completo (cuposRestantes = 0)
    private Boolean disponible;
}