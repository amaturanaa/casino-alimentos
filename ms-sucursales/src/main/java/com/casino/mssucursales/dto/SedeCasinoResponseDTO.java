package com.casino.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeCasinoResponseDTO {

    private Long idSedeCasino;
    private String nombreSede;
    private String direccion;
    private Integer capacidadComensales;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private Boolean estadoOperativo;
}
