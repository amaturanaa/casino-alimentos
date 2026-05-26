package com.casino.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un empleado de sucursal al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {

    // Identificador único del empleado generado por la base de datos
    private Long idEmpleado;

    // RUN del empleado sin dígito verificador
    private String runEmpleado;

    // Nombre completo construido en el Service
    // Combina: pnombre + snombre + appaterno + apmaterno
    private String nombreCompleto;

    // Cargo del empleado en la sucursal
    private String cargo;

    // Tipo de jornada laboral del empleado
    private String jornada;

    // Sueldo base mensual en pesos chilenos
    private Integer sueldoBase;
}