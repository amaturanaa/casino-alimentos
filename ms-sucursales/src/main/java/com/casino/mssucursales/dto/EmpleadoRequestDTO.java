package com.casino.mssucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank
    @Size(max = 8)
    private String runEmpleado;

    @NotBlank
    @Size(max = 1)
    private String dvRunEmpleado;

    @NotBlank
    @Size(max = 20)
    private String pnombreEmpleado;

    @Size(max = 20)
    private String snombreEmpleado;

    @NotBlank
    @Size(max = 20)
    private String appaternoEmpleado;

    @Size(max = 20)
    private String apmaternoEmpleado;

    @NotNull
    @Min(1)
    private Integer sueldoBase;

    @NotBlank
    private String cargo;

    @NotBlank
    private String jornada;
}
