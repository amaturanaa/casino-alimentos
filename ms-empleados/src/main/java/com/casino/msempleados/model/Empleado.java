package com.casino.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    @Column(nullable = false, unique = true, length = 8)
    private String rutEmpleado;

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

    @Column(nullable = false, length = 30)
    private String cargo;

    @Column(nullable = false, length = 15)
    private String jornada;

    @Column(nullable = false)
    private Boolean activo = true;

    // Referencia a ms-usuarios
    @Column
    private Long usuarioId;
}