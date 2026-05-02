package com.casino.msmenu.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatoRequestDTO {

    @NotNull
    private String nombrePlato;

    private String descripcionPlato;

    @NotNull @Min(0)
    private Double precioReferencial;

    @NotNull
    private Long tipoPlatoId;

    @NotNull
    private Long categoriaId;


}
