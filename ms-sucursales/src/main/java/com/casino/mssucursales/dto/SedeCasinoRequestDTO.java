package com.casino.mssucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeCasinoRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String nombreSede;

    @NotBlank
    @Size(max = 100)
    private String direccion;

    @NotNull
    @Min(1)
    private Integer capacidadComensales;

    @NotNull
    private LocalTime horaApertura;

    @NotNull
    private LocalTime horaCierre;
}
