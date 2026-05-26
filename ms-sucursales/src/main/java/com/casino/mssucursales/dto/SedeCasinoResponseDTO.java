package com.casino.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

// DTO de salida para retornar datos de una sede de casino al cliente
// Evita exponer la entidad JPA directamente
// Este DTO es replicado localmente en ms-inventario, ms-proveedores,
// ms-empleados, ms-reservas y ms-pedidos para deserializar la respuesta Feign
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeCasinoResponseDTO {

    // Identificador único de la sede generado por la base de datos
    private Long idSedeCasino;

    // Nombre de la sede del casino
    private String nombreSede;

    // Dirección física de la sede
    private String direccion;

    // Capacidad máxima de comensales
    private Integer capacidadComensales;

    // Hora de apertura de la sede
    private LocalTime horaApertura;

    // Hora de cierre de la sede
    private LocalTime horaCierre;

    // Estado operativo de la sede
    // true = operativa (acepta pedidos, turnos, reservas, etc.)
    // false = no operativa — los microservicios que llaman via Feign
    //         rechazarán crear recursos asociados a esta sede
    private Boolean estadoOperativo;
}