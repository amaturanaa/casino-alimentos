package com.casino.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {
    private Long idEmpleado;
    private String rutEmpleado;
    private String dvRunEmpleado;
    private String nombreCompleto;
    private String cargo;
    private String jornada;
    private Integer sueldoBase;
    private Boolean activo;
    private Long usuarioId;
}