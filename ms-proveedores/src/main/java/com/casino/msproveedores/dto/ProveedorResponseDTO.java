package com.casino.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un proveedor al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorResponseDTO {

    // Identificador único del proveedor generado por la base de datos
    private Long idProveedor;

    // RUT del proveedor sin dígito verificador
    private String rutProveedor;

    // Dígito verificador del RUT
    private String dvRunProveedor;

    // Razón social de la empresa proveedora
    private String razonSocial;

    // Email de contacto del proveedor
    private String emailProveedor;

    // Número de teléfono del proveedor
    private String telefono;

    // Estado del proveedor en el sistema
    // true = activo (puede recibir órdenes de compra)
    // false = inactivo (baja lógica sin eliminar el registro)
    private Boolean activo;
}