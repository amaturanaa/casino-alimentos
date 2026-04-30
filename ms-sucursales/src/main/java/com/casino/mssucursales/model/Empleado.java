package com.casino.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    @Column(nullable = false, length = 8, unique = true)
    private String runEmpleado;

    @Column(nullable = false, length = 1)
    private String dvRunEmpleado;

    @Column(nullable = false, length = 20)
    private String pnombreEmpleado;

    @Column(length = 20)
    private String snombreEmpleado;

    @Column(nullable = false, length = 20)
    private String appaternoEmpleado;

    @Column(length = 20)
    private String apmaternoEmpleado;

    @Column(nullable = false)
    private Integer sueldoBase;

    @Column(nullable = false, length = 20)
    private String cargo;

    @Column(nullable = false, length = 15)
    private String jornada;

}
