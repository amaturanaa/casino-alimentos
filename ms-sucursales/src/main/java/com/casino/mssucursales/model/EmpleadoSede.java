package com.casino.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleado_sede")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSede {

    @EmbeddedId
    private EmpleadoSedeId id;


    @MapsId("idEmpleado")
    @ManyToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado empleado;


    @MapsId("idSedeCasino")
    @ManyToOne
    @JoinColumn(name = "idSedeCasino", nullable = false)
    private SedeCasino sedeCasino;
}
