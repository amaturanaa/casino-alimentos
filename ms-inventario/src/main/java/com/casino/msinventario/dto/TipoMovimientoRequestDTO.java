package com.casino.msinventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoMovimientoRequestDTO {

    @NotBlank(message = "El nombre del tipo de movimiento es obligatorio")
    @Size(max = 50, message = "El nombre dl tipo no puede superar los 50 caracteres")
    private String nombreTipoMovimiento;
}
