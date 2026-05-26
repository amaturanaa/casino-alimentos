package com.casino.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de la asignación empleado-sede
// Evita exponer la entidad JPA directamente
// Representa la relación muchos a muchos entre Empleado y SedeCasino
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeResponseDTO {

    // Identificador del empleado asignado
    private Long idEmpleado;

    // Identificador de la sede donde fue asignado
    private Long idSedeCasino;
}