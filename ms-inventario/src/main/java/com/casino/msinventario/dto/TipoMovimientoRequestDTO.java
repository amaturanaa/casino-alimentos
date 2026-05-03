package com.casino.msinventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoMovimientoRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String nombreTipoMovimiento;
}
