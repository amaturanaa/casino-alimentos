package com.casino.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoEmpleadoResponseDTO {
    private Long idTurno;
    private Long idEmpleado;
    private String nombreEmpleado;
    private Long sedeId;
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String tipoTurno;
}