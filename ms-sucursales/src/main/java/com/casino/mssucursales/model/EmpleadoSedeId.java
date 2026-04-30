package com.casino.mssucursales.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeId implements Serializable {

    private Long idEmpleado;
    private Long idSedeCasino;
}
