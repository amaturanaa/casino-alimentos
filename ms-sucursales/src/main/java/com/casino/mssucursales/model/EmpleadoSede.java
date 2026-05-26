package com.casino.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "empleado_sede" en db_sucursales
// Implementa la relación muchos a muchos entre Empleado y SedeCasino
// Usa clave primaria compuesta (idEmpleado + idSedeCasino) via @EmbeddedId
@Entity
@Table(name = "empleado_sede")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSede {

    // Clave primaria compuesta embebida
    // Contiene idEmpleado e idSedeCasino juntos como identificador único
    @EmbeddedId
    private EmpleadoSedeId id;

    // Relación ManyToOne con Empleado
    // @MapsId mapea el campo idEmpleado de la clave compuesta
    // @JoinColumn define la columna FK en la tabla empleado_sede
    @MapsId("idEmpleado")
    @ManyToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado empleado;

    // Relación ManyToOne con SedeCasino
    // @MapsId mapea el campo idSedeCasino de la clave compuesta
    @MapsId("idSedeCasino")
    @ManyToOne
    @JoinColumn(name = "idSedeCasino", nullable = false)
    private SedeCasino sedeCasino;
}