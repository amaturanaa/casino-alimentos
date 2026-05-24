package com.casino.mscategoriasmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de una categoría de menú al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// Este mismo DTO es replicado localmente en ms-menu para deserializar
// la respuesta Feign sin crear dependencia entre microservicios
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMenuResponseDTO {

    // Identificador único de la categoría generado por la base de datos
    private Long id;

    // Nombre descriptivo de la categoría
    // Ejemplo: "Almuerzo Completo", "Ensaladas", "Postres", "Bebidas"
    private String nombre;

    // Estado de la categoría en el sistema
    // true = activa, false = inactiva
    // ms-menu verifica este campo via Feign antes de crear un plato
    private Boolean estado;
}