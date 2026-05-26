package com.casino.msreservas.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-sucursales via Feign Client
// Es una copia simplificada del DTO de ms-sucursales
// Solo contiene los campos necesarios para la validación de sede
// Cada microservicio que consume otro debe tener su propio DTO local
// Evita dependencias entre proyectos — cada MS es independiente
@Data
public class SedeCasinoResponseDTO {

    // Identificador único de la sede en ms-sucursales
    private Long idSedeCasino;

    // Nombre de la sede para mostrar en mensajes de error
    // Ejemplo: "Casino Central", "Casino Norte", "Casino Sur"
    private String nombreSede;

    // Estado operativo de la sede
    // true = operativa (acepta reservas)
    // false = no operativa (el Service rechaza crear reservas para esta sede)
    private Boolean estadoOperativo;
}