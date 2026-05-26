package com.casino.mspagos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para procesar un pago
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class TransaccionRequestDTO {

    // Identificador del pedido a pagar
    // @NotNull valida que no sea nulo
    // Referencia a ms-pedidos sin FK física entre bases de datos
    // El Service verifica que no exista ya un pago para este pedido
    @NotNull(message = "El id del pedido es obligatorio")
    private Long pedidoId;

    // Identificador del usuario que realiza el pago
    // @NotNull valida que no sea nulo
    // Referencia a ms-usuarios sin FK física entre bases de datos
    @NotNull(message = "El id usuario es obligatorio")
    private Long usuarioId;

    // Monto total a pagar en pesos chilenos
    // @NotNull valida que no sea nulo
    // @Min valida que el monto no sea negativo
    @NotNull
    @Min(value = 0, message = "El monto no puede ser negativo")
    private Double monto;

    // Método de pago utilizado por el usuario
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // Valores válidos: TARJETA_CREDITO, JUNAEB, SUBSIDIO_EMPRESA, EFECTIVO
    @NotBlank(message = "Metodo de pago es obligatorio")
    private String metodoPago;
}