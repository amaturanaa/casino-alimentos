package com.casino.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

// Entidad JPA que representa la tabla "sede_casino" en db_sucursales
// @Entity indica que esta clase es una entidad persistente en la base de datos
// Es el recurso principal de ms-sucursales — consumido por múltiples microservicios
// via Feign: ms-inventario, ms-proveedores, ms-empleados, ms-reservas, ms-pedidos
@Entity
@Table(name = "sede_casino")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeCasino {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSedeCasino;

    // Nombre de la sede del casino
    // length = 100 limita el tamaño de la columna
    @Column(nullable = false, length = 100)
    private String nombreSede;

    // Dirección física de la sede
    // length = 200 limita el tamaño de la columna
    @Column(nullable = false, length = 200)
    private String direccion;

    // Capacidad máxima de comensales de la sede
    @Column(nullable = false)
    private Integer capacidadComensales;

    // Hora de apertura de la sede en formato HH:mm:ss
    // LocalTime almacena solo la hora sin fecha
    @Column(nullable = false)
    private LocalTime horaApertura;

    // Hora de cierre de la sede en formato HH:mm:ss
    @Column(nullable = false)
    private LocalTime horaCierre;

    // Estado operativo de la sede
    // true = operativa — los microservicios pueden crear recursos asociados
    // false = no operativa — los microservicios via Feign rechazarán crear recursos
    // Valor por defecto: true (nueva sede siempre operativa)
    @Column(nullable = false)
    private Boolean estadoOperativo = true;
}