package com.casino.msreservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

// Entidad JPA que representa la tabla "turno_disponible" en db_reservas
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Define los horarios de comedor disponibles por sede y fecha con control de cupos
@Entity
@Table(name = "turno_disponible")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDisponible {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    // Referencia a la sede en ms-sucursales donde se ofrece el turno
    // No es FK física — cada microservicio maneja su propia base de datos
    @Column(nullable = false)
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    // LocalDate almacena solo la fecha sin hora
    @Column(nullable = false)
    private LocalDate fecha;

    // Hora de inicio del turno en formato HH:mm:ss
    // LocalTime almacena solo la hora sin fecha
    @Column(nullable = false)
    private LocalTime horaInicio;

    // Hora de fin del turno en formato HH:mm:ss
    @Column(nullable = false)
    private LocalTime horaFin;

    // Capacidad máxima de comensales para este turno
    // Se mantiene fijo después de crear el turno
    @Column(nullable = false)
    private Integer capacidad;

    // Cupos restantes disponibles para reservar
    // Se inicializa igual a capacidad al crear el turno
    // Se decrementa cuando se crea una reserva
    // Se incrementa cuando se cancela una reserva
    @Column(nullable = false)
    private Integer cuposRestantes;
}