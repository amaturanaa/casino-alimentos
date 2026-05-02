package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "programacion_diaria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramacion;

    @Column(nullable = false)
    private LocalDate fecha;

    // Referencia a ms-sucursales (solo Long, sin FK física)
    @Column(nullable = false)
    private Long sedeId;

    @ManyToOne
    @JoinColumn(name = "plato_id", nullable = false)
    private Plato plato;

    @Column(nullable = false)
    private Integer racionesDisponibles;
}
