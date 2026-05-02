package com.casino.msmenu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoPlatoRequestDTO {

    @NotNull
    @Size(max = 50)
    private String nombreTipoPlato;

}
