package com.casino.mscategoriasmenu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EtiquetaNutricionalRequestDTO {

    @NotNull
    private Long categoriaId;

    @NotNull @Min(0)
    private Integer calorias;

    @NotNull @Min(0)
    private Double proteinas;

    @NotNull @Min(0)
    private Double carbohidratos;

    @NotNull @Min(0)
    private Double grasas;

    @NotNull
    private Boolean esVegetariano;

    @NotNull
    private Boolean esVegano;

    @NotNull
    private Boolean contieneGluten;
}
