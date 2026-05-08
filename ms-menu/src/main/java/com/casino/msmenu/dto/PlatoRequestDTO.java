package com.casino.msmenu.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlatoRequestDTO {

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(max = 100, message = "El nombre del plato no puede superar los 100 caracteres")
    private String nombrePlato;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcionPlato;

    @NotNull(message = "El precio referencial es obligatorio")
    @Min(value = 0, message = "El precio referencial no puede ser negativo")
    private Double precioReferencial;

    @NotNull(message = "El tipo de plato es obligatorio")
    private Long tipoPlatoId;

    @NotNull(message = "La categoría del menú es obligatoria")
    private Long categoriaId;
}
