package com.casino.mssucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

// DTO de entrada para crear una sede de casino
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeCasinoRequestDTO {

    // Nombre de la sede del casino
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // Ejemplo: "Casino Central", "Casino Norte", "Casino Sur"
    @NotBlank
    @Size(max = 30)
    private String nombreSede;

    // Dirección física de la sede
    @NotBlank
    @Size(max = 100)
    private String direccion;

    // Capacidad máxima de comensales de la sede
    // @Min valida que sea al menos 1
    @NotNull
    @Min(1)
    private Integer capacidadComensales;

    // Hora de apertura de la sede en formato HH:mm:ss
    // LocalTime almacena solo la hora sin fecha
    @NotNull
    private LocalTime horaApertura;

    // Hora de cierre de la sede en formato HH:mm:ss
    @NotNull
    private LocalTime horaCierre;
}