package com.casino.mssucursales.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de entrada para asignar un empleado a una sede
// Separa los datos de entrada de la entidad JPA interna
// Usa clave primaria compuesta (idEmpleado + idSedeCasino)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeRequestDTO {

    // Identificador del empleado a asignar
    // @NotNull valida que no sea nulo
    @NotNull
    private Long idEmpleado;

    // Identificador de la sede donde se asigna el empleado
    // @NotNull valida que no sea nulo
    @NotNull
    private Long idSedeCasino;
}