package com.casino.msproveedores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear o actualizar un proveedor
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class ProveedorRequestDTO {

    // RUT del proveedor sin dígito verificador
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // @Size limita la longitud máxima del campo
    @NotBlank
    @Size(max = 8)
    private String rutProveedor;

    // Dígito verificador del RUT — máximo 1 carácter (número o K)
    @NotBlank
    @Size(max = 1)
    private String dvRunProveedor;

    // Razón social de la empresa proveedora
    // @Size limita la longitud máxima del campo en la base de datos
    @NotBlank
    @Size(max = 100)
    private String razonSocial;

    // Email de contacto del proveedor
    // @Email valida que tenga formato de correo electrónico válido
    @NotBlank
    @Email
    private String emailProveedor;

    // Número de teléfono del proveedor
    // @Size limita la longitud máxima del campo
    @NotBlank
    @Size(max = 15)
    private String telefono;
}