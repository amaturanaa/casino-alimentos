package com.casino.msreservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// Entidad JPA que representa la tabla "reserva" en db_reservas
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Registra cada reserva de un usuario en un turno disponible de una sede
@Entity
@Table(name = "reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    // Referencia al usuario en ms-usuarios que realizó la reserva
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de usuario se hace via Feign Client en el Service al crear
    @Column(nullable = false)
    private Long usuarioId;

    // Relación ManyToOne con TurnoDisponible dentro del mismo microservicio
    // Muchas reservas pueden pertenecer al mismo turno disponible
    // @JoinColumn define la columna FK en la tabla reserva
    // nullable = false indica que toda reserva debe tener un turno asociado
    @ManyToOne
    @JoinColumn(name = "turno_id", nullable = false)
    private TurnoDisponible turno;

    // Referencia a la sede en ms-sucursales donde se realiza la reserva
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de sede se hace via Feign Client en el Service al crear
    @Column(nullable = false)
    private Long sedeId;

    // Fecha y hora exacta en que se creó la reserva
    // LocalDateTime almacena fecha y hora sin zona horaria
    // Se asigna automáticamente en el Service con LocalDateTime.now()
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    // Estado actual de la reserva
    // length = 20 limita el tamaño de la columna en la base de datos
    // Ejemplo: "ACTIVA", "CANCELADA"
    @Column(nullable = false, length = 20)
    private String estado;
}