package com.casino.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO {
    private Long idReserva;
    private Long usuarioId;
    private Long turnoId;
    private Long sedeId;
    private LocalDateTime fechaCreacion;
    private String estado;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}