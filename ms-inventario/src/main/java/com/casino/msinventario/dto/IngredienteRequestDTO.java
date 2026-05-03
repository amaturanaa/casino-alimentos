package com.casino.msinventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IngredienteRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String nombreIngrediente;

    @NotNull
    private Long sedeId;

    @NotBlank
    @Size(max = 20)
    private String unidadMedida;

    @NotNull @Min(0)
    private Double stockActual;

    @NotNull @Min(0)
    private Double stockMinimo;
}
