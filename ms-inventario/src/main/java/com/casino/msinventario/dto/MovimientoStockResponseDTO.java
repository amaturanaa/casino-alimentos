package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoStockResponseDTO {

    private Long idMovimiento;
    private Long ingredienteId;
    private String nombreIngrediente;
    private String tipoMovimiento;
    private Double cantidad;
    private LocalDateTime fechaMovimiento;
    private String motivo;
    private Double stockResultante;
}
