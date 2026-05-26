package com.casino.mssucursales.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

// Clase de clave primaria compuesta para la tabla empleado_sede
// @Embeddable indica que puede ser embebida en otra entidad JPA
// Implements Serializable es requerido por JPA para claves compuestas
// Representa la relación única entre un empleado y una sede
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeId implements Serializable {

    // Identificador del empleado — parte de la clave compuesta
    private Long idEmpleado;

    // Identificador de la sede — parte de la clave compuesta
    // La combinación idEmpleado + idSedeCasino garantiza unicidad
    private Long idSedeCasino;
}