package com.casino.mscategoriasmenu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaMenuRequestDTO {

    @NotNull
    private String nombre;

    @NotNull
    private Boolean estado;
}
