package com.casino.msproveedores.dto;

import lombok.Data;

@Data
public class SedeCasinoResponseDTO {
    private Long idSedeCasino;
    private String nombreSede;
    private Boolean estadoOperativo;
}