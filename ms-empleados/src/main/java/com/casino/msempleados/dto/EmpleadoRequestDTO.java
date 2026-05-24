package com.casino.msempleados.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear o actualizar un empleado
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class EmpleadoRequestDTO {

    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 8, message = "El RUT no puede superar 8 caracteres")
    private String rutEmpleado;

    // Dígito verificador del RUT — máximo 1 carácter (número o K)
    @NotBlank(message = "El dígito verificador es obligatorio")
    @Size(max = 1)
    private String dvRunEmpleado;

    // Primer nombre obligatorio — máximo 20 caracteres
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 20)
    private String pnombreEmpleado;

    // Segundo nombre opcional — puede ser nulo
    @Size(max = 20)
    private String snombreEmpleado;

    // Apellido paterno obligatorio — máximo 20 caracteres
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 20)
    private String appaternoEmpleado;

    // Apellido materno opcional — puede ser nulo
    @Size(max = 20)
    private String apmaternoEmpleado;

    // @NotNull valida que no sea nulo (para tipos no String como Integer)
    // @Min valida que el valor sea mayor o igual al mínimo indicado
    @NotNull(message = "El sueldo base es obligatorio")
    @Min(value = 1, message = "El sueldo base debe ser mayor a 0")
    private Integer sueldoBase;

    // Cargo del empleado — máximo 30 caracteres
    // Ejemplo: Cocinero, Cajero, Mesero, Supervisor
    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 30)
    private String cargo;

    // Tipo de jornada laboral — máximo 15 caracteres
    // Ejemplo: COMPLETA, PARCIAL
    @NotBlank(message = "La jornada es obligatoria")
    @Size(max = 15)
    private String jornada;

    // Referencia al usuario del sistema (ms-usuarios)
    // Campo opcional — el empleado puede no tener usuario asociado
    private Long usuarioId;
}