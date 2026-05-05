package com.casino.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turno_empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Referencia a ms-sucursales
    @Column(nullable = false)
    private Long sedeId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaEntrada;

    @Column(nullable = false)
    private LocalTime horaSalida;

    @Column(nullable = false, length = 20)
    private String tipoTurno;
}