package com.casino.msmenu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProgramacionDiariaRequestDTO {

    @NotNull(message = "La fecha de la programación es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El id de la sede es obligatorio")
    private Long sedeId;

    @NotNull(message = "El id del plato es obligatorio")
    private Long platoId;

    @NotNull(message = "Las raciones disponibles son obligatorias")
    @Min(value = 1, message = "Las raciones disponibles deben ser al menos 1")
    private Integer racionesDisponibles;
}
