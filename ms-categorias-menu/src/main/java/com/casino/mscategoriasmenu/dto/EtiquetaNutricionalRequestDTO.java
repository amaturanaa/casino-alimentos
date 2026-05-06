package com.casino.mscategoriasmenu.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EtiquetaNutricionalRequestDTO {

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @NotNull(message = "Las calorías son obligatorias")
    @Min(value = 0, message = "Las calorías no pueden ser negativas")
    private Integer calorias;

    @NotNull(message = "Las proteínas son obligatorias")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Las proteínas no pueden ser negativas")
    private Double proteinas;

    @NotNull(message = "Los carbohidratos son obligatorios")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Los carbohidratos no pueden ser negativos")
    private Double carbohidratos;

    @NotNull(message = "Las grasas son obligatorias")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Las grasas no pueden ser negativas")
    private Double grasas;

    @NotNull(message = "Debe indicar si es vegetariano")
    private Boolean esVegetariano;

    @NotNull(message = "Debe indicar si es vegano")
    private Boolean esVegano;

    @NotNull(message = "Debe indicar si contiene gluten")
    private Boolean contieneGluten;
}
