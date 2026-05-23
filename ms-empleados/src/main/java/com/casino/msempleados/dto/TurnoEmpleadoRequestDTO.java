package com.casino.msempleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

// DTO de entrada para crear un turno de empleado
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class TurnoEmpleadoRequestDTO {

    // Identificador del empleado al que se asigna el turno
    // @NotNull valida que no sea nulo (para tipos no String como Long)
    @NotNull(message = "El empleado es obligatorio")
    private Long idEmpleado;

    // Identificador de la sede donde trabajará el empleado
    // Se valida via Feign Client que la sede exista y esté operativa en ms-sucursales
    @NotNull(message = "La sede es obligatoria")
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    // Ejemplo: 2026-05-15
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    // Hora de entrada al turno en formato HH:mm:ss
    // Ejemplo: 08:00:00
    @NotNull(message = "La hora de entrada es obligatoria")
    private LocalTime horaEntrada;

    // Hora de salida del turno en formato HH:mm:ss
    // Ejemplo: 17:00:00
    @NotNull(message = "La hora de salida es obligatoria")
    private LocalTime horaSalida;

    // Tipo de turno laboral
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // Ejemplo: MAÑANA, TARDE, NOCHE
    @NotBlank(message = "El tipo de turno es obligatorio")
    private String tipoTurno;
}