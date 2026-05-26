package com.casino.mssucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de entrada para crear un empleado de sucursal
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequestDTO {

    // RUN del empleado sin dígito verificador — identificador único
    @NotBlank
    @Size(max = 8)
    private String runEmpleado;

    // Dígito verificador del RUN — máximo 1 carácter
    @NotBlank
    @Size(max = 1)
    private String dvRunEmpleado;

    // Primer nombre del empleado — obligatorio
    @NotBlank
    @Size(max = 20)
    private String pnombreEmpleado;

    // Segundo nombre del empleado — opcional
    @Size(max = 20)
    private String snombreEmpleado;

    // Apellido paterno del empleado — obligatorio
    @NotBlank
    @Size(max = 20)
    private String appaternoEmpleado;

    // Apellido materno del empleado — opcional
    @Size(max = 20)
    private String apmaternoEmpleado;

    // Sueldo base mensual del empleado en pesos chilenos
    // @Min valida que sea al menos 1
    @NotNull
    @Min(1)
    private Integer sueldoBase;

    // Cargo del empleado en la sucursal
    // Ejemplo: Cocinero, Cajero, Supervisor
    @NotBlank
    private String cargo;

    // Tipo de jornada laboral
    // Ejemplo: COMPLETA, PARCIAL
    @NotBlank
    private String jornada;
}