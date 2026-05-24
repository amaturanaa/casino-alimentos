package com.casino.msmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un plato al cliente
// Evita exponer la entidad JPA directamente
// Incluye el nombre del tipo de plato en vez del id para enriquecer la respuesta
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoResponseDTO {

    // Identificador único del plato generado por la base de datos
    private Long idPlato;

    // Nombre descriptivo del plato
    // Ejemplo: "Pollo a la plancha", "Cazuela de vacuno"
    private String nombrePlato;

    // Descripción detallada del plato — puede ser null si no fue ingresada
    private String descripcionPlato;

    // Precio referencial del plato en pesos chilenos
    private Double precioReferencial;

    // Nombre del tipo de plato — obtenido desde la entidad TipoPlato
    // Evita que el cliente tenga que hacer una segunda consulta para obtener el nombre
    // Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
    private String nombreTipoPlato;

    // Identificador de la categoría en ms-categorias-menu
    // Referencia sin FK física — cada microservicio maneja su propia base de datos
    private Long categoriaId;

    // Estado de disponibilidad del plato en el sistema
    // true = disponible (puede ser incluido en pedidos)
    // false = no disponible (no puede ser incluido en pedidos)
    private Boolean disponible;
}