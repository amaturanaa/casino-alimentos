package com.casino.msempleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoEmpleadoRequestDTO {

    @NotNull(message = "El empleado es obligatorio")
    private Long idEmpleado;

    @NotNull(message = "La sede es obligatoria")
    private Long sedeId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de entrada es obligatoria")
    private LocalTime horaEntrada;

    @NotNull(message = "La hora de salida es obligatoria")
    private LocalTime horaSalida;

    @NotBlank(message = "El tipo de turno es obligatorio")
    private String tipoTurno;
}