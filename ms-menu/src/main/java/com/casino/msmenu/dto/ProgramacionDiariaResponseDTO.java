package com.casino.msmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDiariaResponseDTO {

    private Long idProgramacion;
    private LocalDate fecha;
    private Long sedeId;
    private Long platoId;
    private String nombrePlato;
    private Integer racionesDisponibles;
}
