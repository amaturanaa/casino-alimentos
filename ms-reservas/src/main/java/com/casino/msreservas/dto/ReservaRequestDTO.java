package com.casino.msreservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para crear una reserva
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Al crear una reserva se verifican usuario y sede via Feign
@Data
public class ReservaRequestDTO {

    // Identificador del usuario que realiza la reserva
    // @NotNull valida que no sea nulo
    // Se valida via Feign Client que el usuario exista y esté activo en ms-usuarios
    @NotNull
    private Long usuarioId;

    // Identificador del turno disponible a reservar
    // @NotNull valida que no sea nulo
    // El Service verifica que el turno exista y tenga cupos disponibles
    @NotNull
    private Long turnoId;

    // Identificador de la sede donde se realiza la reserva
    // @NotNull valida que no sea nulo
    // Se valida via Feign Client que la sede exista y esté operativa en ms-sucursales
    @NotNull
    private Long sedeId;
}