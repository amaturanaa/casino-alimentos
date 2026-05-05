package com.casino.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDisponibleResponseDTO {
    private Long idTurno;
    private Long sedeId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer capacidad;
    private Integer cuposRestantes;
    private Boolean disponible;
}