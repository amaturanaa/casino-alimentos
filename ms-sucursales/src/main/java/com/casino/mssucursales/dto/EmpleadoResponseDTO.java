package com.casino.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {

    private Long idEmpleado;
    private String runEmpleado;
    private String nombreCompleto;
    private String cargo;
    private String jornada;
    private Integer sueldoBase;
}
