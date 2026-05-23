package com.casino.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "empleado" en db_empleados
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Sigue el patrón CSR: la entidad solo representa datos, sin lógica de negocio
@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    // RUT del empleado sin dígito verificador
    // unique = true garantiza que no se repita el RUT en la base de datos
    // length = 8 limita el tamaño de la columna en la base de datos
    @Column(nullable = false, unique = true, length = 8)
    private String rutEmpleado;

    // Dígito verificador del RUT — máximo 1 carácter (número o K)
    @Column(nullable = false, length = 1)
    private String dvRunEmpleado;

    // Primer nombre del empleado — obligatorio
    @Column(nullable = false, length = 20)
    private String pnombreEmpleado;

    // Segundo nombre del empleado — opcional (puede ser null)
    @Column(length = 20)
    private String snombreEmpleado;

    // Apellido paterno del empleado — obligatorio
    @Column(nullable = false, length = 20)
    private String appaternoEmpleado;

    // Apellido materno del empleado — opcional (puede ser null)
    @Column(length = 20)
    private String apmaternoEmpleado;

    // Sueldo base mensual del empleado en pesos chilenos
    @Column(nullable = false)
    private Integer sueldoBase;

    // Cargo del empleado en el casino
    // Ejemplo: Cocinero, Cajero, Mesero, Supervisor, Jefe de Cocina
    @Column(nullable = false, length = 30)
    private String cargo;

    // Tipo de jornada laboral del empleado
    // Ejemplo: COMPLETA, PARCIAL
    @Column(nullable = false, length = 15)
    private String jornada;

    // Estado del empleado en el sistema
    // true = activo, false = inactivo (baja lógica sin eliminar el registro)
    // Valor por defecto: true (nuevo empleado siempre activo)
    @Column(nullable = false)
    private Boolean activo = true;

    // Referencia al usuario del sistema en ms-usuarios
    // No es FK física — cada microservicio maneja su propia base de datos
    // La comunicación real se hace via Feign Client cuando es necesario
    @Column
    private Long usuarioId;
}