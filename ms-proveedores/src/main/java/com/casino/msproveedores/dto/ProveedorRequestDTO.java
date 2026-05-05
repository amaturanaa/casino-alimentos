package com.casino.msproveedores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedorRequestDTO {
    @NotBlank
    @Size(max = 8)
    private String rutProveedor;

    @NotBlank
    @Size(max = 1)
    private String dvRunProveedor;

    @NotBlank
    @Size(max = 100)
    private String razonSocial;

    @NotBlank
    @Email
    private String emailProveedor;

    @NotBlank
    @Size(max = 15)
    private String telefono;
}
