package com.casino.msempleados.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 8, message = "El RUT no puede superar 8 caracteres")
    private String rutEmpleado;

    @NotBlank(message = "El dígito verificador es obligatorio")
    @Size(max = 1)
    private String dvRunEmpleado;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 20)
    private String pnombreEmpleado;

    @Size(max = 20)
    private String snombreEmpleado;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 20)
    private String appaternoEmpleado;

    @Size(max = 20)
    private String apmaternoEmpleado;

    @NotNull(message = "El sueldo base es obligatorio")
    @Min(value = 1, message = "El sueldo base debe ser mayor a 0")
    private Integer sueldoBase;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 30)
    private String cargo;

    @NotBlank(message = "La jornada es obligatoria")
    @Size(max = 15)
    private String jornada;

    private Long usuarioId;
}