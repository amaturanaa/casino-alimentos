package com.casino.msempleados.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-sucursales via Feign Client
// Es una copia simplificada del DTO de ms-sucursales
// Solo contiene los campos necesarios para la validación de sede
// Cada microservicio que consume otro debe tener su propio DTO local
@Data
public class SedeCasinoResponseDTO {

    // Identificador único de la sede en ms-sucursales
    private Long idSedeCasino;

    // Nombre de la sede para mostrar en mensajes de error
    private String nombreSede;

    // Estado operativo de la sede
    // true = operativa (acepta pedidos, turnos, etc.)
    // false = no operativa (no se pueden crear recursos asociados)
    private Boolean estadoOperativo;
}