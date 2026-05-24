package com.casino.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

// Entidad JPA que representa la tabla "turno_empleado" en db_empleados
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
@Entity
@Table(name = "turno_empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoEmpleado {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    // Relación ManyToOne con la entidad Empleado dentro del mismo microservicio
    // Un empleado puede tener muchos turnos
    // @JoinColumn define la columna FK en la tabla turno_empleado
    // nullable = false indica que todo turno debe tener un empleado asignado
    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Referencia a la sede en ms-sucursales
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de sede se hace via Feign Client en el Service
    @Column(nullable = false)
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    // LocalDate almacena solo la fecha sin hora
    @Column(nullable = false)
    private LocalDate fecha;

    // Hora de entrada al turno en formato HH:mm:ss
    // LocalTime almacena solo la hora sin fecha
    @Column(nullable = false)
    private LocalTime horaEntrada;

    // Hora de salida del turno en formato HH:mm:ss
    @Column(nullable = false)
    private LocalTime horaSalida;

    // Tipo de turno laboral asignado al empleado
    // Ejemplo: MAÑANA, TARDE, NOCHE
    @Column(nullable = false, length = 20)
    private String tipoTurno;
}