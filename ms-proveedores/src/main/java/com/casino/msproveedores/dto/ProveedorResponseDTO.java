package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProveedorResponseDTO {
    private Long idProveedor;
    private String rutProveedor;
    private String dvRunProveedor;
    private String razonSocial;
    private String emailProveedor;
    private String telefono;
    private Boolean activo;
}

