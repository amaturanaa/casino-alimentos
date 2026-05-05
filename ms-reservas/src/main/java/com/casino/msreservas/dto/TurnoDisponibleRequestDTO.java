package com.casino.msreservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDisponibleRequestDTO {
    @NotNull
    private Long sedeId;
    @NotNull
    private LocalDate fecha;
    @NotNull
    private LocalTime horaInicio;
    @NotNull
    private LocalTime horaFin;
    @NotNull @Min(1)
    private Integer capacidad;
}