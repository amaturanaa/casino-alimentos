package com.casino.mspedidos.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-sucursales via Feign Client
// Solo contiene los campos necesarios para la validación de sede
@Data
public class SedeCasinoResponseDTO {
    private Long idSedeCasino;
    private String nombreSede;
    private Boolean estadoOperativo;
}