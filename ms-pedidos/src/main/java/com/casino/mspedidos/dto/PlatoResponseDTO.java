package com.casino.mspedidos.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-menu via Feign Client
// Solo contiene los campos necesarios para la validación de plato
@Data
public class PlatoResponseDTO {
    private Long idPlato;
    private String nombrePlato;
    private Boolean disponible;
    private Double precioReferencial;
}