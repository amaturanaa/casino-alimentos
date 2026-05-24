package com.casino.mscategoriasmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de una etiqueta nutricional al cliente
// Evita exponer la entidad JPA directamente
// Incluye datos de la categoría asociada para enriquecer la respuesta
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaNutricionalResponseDTO {

    // Identificador único de la etiqueta generado por la base de datos
    private Long id;

    // Identificador de la categoría asociada a esta etiqueta
    // Permite al cliente relacionar la etiqueta con su categoría
    private Long categoriaId;

    // Nombre de la categoría asociada — obtenido desde la entidad CategoriaMenu
    // Evita que el cliente tenga que hacer una segunda consulta para obtener el nombre
    private String nombreCategoria;

    // Cantidad de calorías por porción del plato
    private Integer calorias;

    // Cantidad de proteínas en gramos por porción
    private Double proteinas;

    // Cantidad de carbohidratos en gramos por porción
    private Double carbohidratos;

    // Cantidad de grasas en gramos por porción
    private Double grasas;

    // Indica si el plato es apto para dieta vegetariana
    // true = apto para vegetarianos
    private Boolean esVegetariano;

    // Indica si el plato es apto para dieta vegana
    // true = apto para veganos
    private Boolean esVegano;

    // Indica si el plato contiene gluten
    // true = contiene gluten (no apto para celíacos)
    // false = libre de gluten (apto para celíacos)
    private Boolean contieneGluten;
}