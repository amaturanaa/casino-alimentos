package com.casino.msreservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

// DTO de entrada para crear un turno disponible
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Los turnos disponibles definen los horarios de comedor por sede y fecha
@Data
public class TurnoDisponibleRequestDTO {

    // Identificador de la sede donde se habilita el turno
    // @NotNull valida que no sea nulo
    // Referencia a ms-sucursales sin FK física entre bases de datos
    @NotNull
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    // @NotNull valida que no sea nulo
    // LocalDate almacena solo la fecha sin hora
    @NotNull
    private LocalDate fecha;

    // Hora de inicio del turno en formato HH:mm:ss
    // @NotNull valida que no sea nulo
    // LocalTime almacena solo la hora sin fecha
    @NotNull
    private LocalTime horaInicio;

    // Hora de fin del turno en formato HH:mm:ss
    @NotNull
    private LocalTime horaFin;

    // Capacidad máxima de comensales para este turno
    // @NotNull valida que no sea nulo
    // @Min valida que la capacidad sea al menos 1
    // Se copia a cuposRestantes al crear el turno
    @NotNull
    @Min(1)
    private Integer capacidad;
}