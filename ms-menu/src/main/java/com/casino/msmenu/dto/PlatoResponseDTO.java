package com.casino.msmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoResponseDTO {

    private Long idPlato;
    private String nombrePlato;
    private String descripcionPlato;
    private Double precioReferencial;
    private String nombreTipoPlato;
    private Long categoriaId;
    private Boolean disponible;
}
