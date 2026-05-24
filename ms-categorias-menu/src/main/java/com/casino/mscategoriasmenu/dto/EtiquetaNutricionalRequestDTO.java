package com.casino.mscategoriasmenu.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para crear o actualizar una etiqueta nutricional
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Cada etiqueta está asociada a una única categoría de menú (relación 1 a 1)
@Data
public class EtiquetaNutricionalRequestDTO {

    // Identificador de la categoría a la que pertenece la etiqueta
    // @NotNull valida que no sea nulo
    // El Service verifica que la categoría exista y no tenga ya una etiqueta
    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    // Cantidad de calorías por porción
    // @Min valida que el valor sea mayor o igual a 0
    @NotNull(message = "Las calorías son obligatorias")
    @Min(value = 0, message = "Las calorías no pueden ser negativas")
    private Integer calorias;

    // Cantidad de proteínas en gramos por porción
    // @DecimalMin valida valores decimales con límite mínimo inclusivo
    @NotNull(message = "Las proteínas son obligatorias")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Las proteínas no pueden ser negativas")
    private Double proteinas;

    // Cantidad de carbohidratos en gramos por porción
    @NotNull(message = "Los carbohidratos son obligatorios")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Los carbohidratos no pueden ser negativos")
    private Double carbohidratos;

    // Cantidad de grasas en gramos por porción
    @NotNull(message = "Las grasas son obligatorias")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Las grasas no pueden ser negativas")
    private Double grasas;

    // Indica si el plato es apto para dieta vegetariana
    // true = apto para vegetarianos
    @NotNull(message = "Debe indicar si es vegetariano")
    private Boolean esVegetariano;

    // Indica si el plato es apto para dieta vegana
    // true = apto para veganos
    @NotNull(message = "Debe indicar si es vegano")
    private Boolean esVegano;

    // Indica si el plato contiene gluten
    // true = contiene gluten (no apto para celíacos)
    // false = libre de gluten (apto para celíacos)
    @NotNull(message = "Debe indicar si contiene gluten")
    private Boolean contieneGluten;
}