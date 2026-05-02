package com.casino.msmenu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProgramacionDiariaRequestDTO {

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Long sedeId;

    @NotNull
    private Long platoId;

    @NotNull @Min(1)
    private Integer racionesDisponibles;
}
