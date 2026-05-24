package com.casino.msmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un tipo de plato al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPlatoResponseDTO {

    // Identificador único del tipo de plato generado por la base de datos
    private Long idTipoPlato;

    // Nombre descriptivo del tipo de plato
    // Usado en PlatoResponseDTO para mostrar el nombre en vez del id
    // Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
    private String nombreTipoPlato;
}