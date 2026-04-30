package com.casino.mssucursales.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "sede_casino")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeCasino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSedeCasino;

    @Column(nullable = false, length = 100)
    private String nombreSede;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false)
    private Integer capacidadComensales;

    @Column(nullable = false)
    private LocalTime horaApertura;

    @Column(nullable = false)
    private LocalTime horaCierre;

    @Column(nullable = false)
    private Boolean estadoOperativo = true;
}
