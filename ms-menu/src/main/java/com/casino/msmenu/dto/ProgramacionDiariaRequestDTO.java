package com.casino.msmenu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

// DTO de entrada para crear una programación diaria de menú
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// La programación diaria define qué platos se sirven en cada sede por fecha
@Data
public class ProgramacionDiariaRequestDTO {

    // Fecha de la programación en formato ISO: yyyy-MM-dd
    // @NotNull valida que no sea nulo
    // LocalDate almacena solo la fecha sin hora
    // Ejemplo: 2026-05-15
    @NotNull(message = "La fecha de la programación es obligatoria")
    private LocalDate fecha;

    // Identificador de la sede donde se programa el menú
    // @NotNull valida que no sea nulo
    // Referencia a ms-sucursales sin FK física entre bases de datos
    @NotNull(message = "El id de la sede es obligatorio")
    private Long sedeId;

    // Identificador del plato que se programa para esa fecha y sede
    // @NotNull valida que no sea nulo
    // Referencia local a la entidad Plato dentro del mismo microservicio
    @NotNull(message = "El id del plato es obligatorio")
    private Long platoId;

    // Cantidad de raciones disponibles del plato para ese día
    // @Min valida que el valor sea al menos 1
    // Se descuenta automáticamente cuando un cliente realiza un pedido
    @NotNull(message = "Las raciones disponibles son obligatorias")
    @Min(value = 1, message = "Las raciones disponibles deben ser al menos 1")
    private Integer racionesDisponibles;
}