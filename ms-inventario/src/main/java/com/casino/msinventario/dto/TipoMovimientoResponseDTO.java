package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMovimientoResponseDTO {

    private Long idTipoMovimiento;

    private String nombreTipoMovimiento;
}
