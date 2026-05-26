package com.casino.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "Empleado" en db_sucursales
// @Entity indica que esta clase es una entidad persistente en la base de datos
// Nota: esta entidad es diferente al Empleado de ms-empleados
// Este empleado pertenece a la sucursal y puede asignarse a múltiples sedes
@Entity
@Table(name = "Empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    // RUN del empleado — identificador único nacional
    // unique = true garantiza que no se repita en la base de datos
    @Column(nullable = false, length = 8, unique = true)
    private String runEmpleado;

    // Dígito verificador del RUN — máximo 1 carácter
    @Column(nullable = false, length = 1)
    private String dvRunEmpleado;

    // Primer nombre del empleado — obligatorio
    @Column(nullable = false, length = 20)
    private String pnombreEmpleado;

    // Segundo nombre del empleado — opcional
    @Column(length = 20)
    private String snombreEmpleado;

    // Apellido paterno del empleado — obligatorio
    @Column(nullable = false, length = 20)
    private String appaternoEmpleado;

    // Apellido materno del empleado — opcional
    @Column(length = 20)
    private String apmaternoEmpleado;

    // Sueldo base mensual en pesos chilenos
    @Column(nullable = false)
    private Integer sueldoBase;

    // Cargo del empleado en la sucursal
    // Ejemplo: Cocinero, Cajero, Supervisor
    @Column(nullable = false, length = 20)
    private String cargo;

    // Tipo de jornada laboral del empleado
    // Ejemplo: COMPLETA, PARCIAL
    @Column(nullable = false, length = 15)
    private String jornada;
}