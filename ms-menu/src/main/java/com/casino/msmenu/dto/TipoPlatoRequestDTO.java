package com.casino.msmenu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoPlatoRequestDTO {

    @NotBlank(message = "El nombre del tipo de plato es obligatorio")
    @Size(max = 50, message = "El nombre del tipo de plato no puede superar los 50 caracteres")
    private String nombreTipoPlato;
}
