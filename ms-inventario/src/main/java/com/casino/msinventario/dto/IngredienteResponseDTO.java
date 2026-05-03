package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteResponseDTO {

    private Long idIngrediente;
    private String nombreIngrediente;
    private Long sedeId;
    private String unidadMedida;
    private Double stockActual;
    private Double stockMinimo;
    private Boolean stockBajo;
}
