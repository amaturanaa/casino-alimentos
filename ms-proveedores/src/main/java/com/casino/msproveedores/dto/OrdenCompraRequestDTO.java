package com.casino.msproveedores.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

// DTO de entrada para crear una orden de compra con sus detalles
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Al crear la orden se verifica la sede via Feign con ms-sucursales
@Data
public class OrdenCompraRequestDTO {

    // Identificador del proveedor que recibirá la orden
    // @NotNull valida que no sea nulo
    // Referencia local a la entidad Proveedor dentro del mismo microservicio
    @NotNull
    private Long proveedorId;

    // Identificador de la sede que realiza la compra
    // @NotNull valida que no sea nulo
    // Se valida via Feign Client que la sede exista y esté operativa en ms-sucursales
    @NotNull
    private Long sedeId;

    // Lista de detalles de la orden — uno por cada producto a comprar
    // @NotNull valida que la lista no sea nula
    @NotNull
    private List<DetalleOrdenCompraRequestDTO> detalles;
}