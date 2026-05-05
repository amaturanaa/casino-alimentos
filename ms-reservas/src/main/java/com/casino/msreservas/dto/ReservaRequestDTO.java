package com.casino.msreservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaRequestDTO {
    @NotNull
    private Long usuarioId;
    @NotNull
    private Long turnoId;
    @NotNull
    private Long sedeId;
}