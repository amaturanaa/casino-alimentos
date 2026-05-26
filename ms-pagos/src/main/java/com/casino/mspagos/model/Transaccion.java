package com.casino.mspagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// Entidad JPA que representa la tabla "transaccion" en db_pagos
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Registra cada transacción de pago realizada en el sistema
@Entity
@Table(name = "transaccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    // Referencia al pedido en ms-pedidos asociado a este pago
    // No es FK física — cada microservicio maneja su propia base de datos
    // Un pedido solo puede tener un pago (relación 1 a 1 verificada en el Service)
    @Column(nullable = false)
    private Long pedidoId;

    // Referencia al usuario en ms-usuarios que realizó el pago
    // No es FK física — cada microservicio maneja su propia base de datos
    @Column(nullable = false)
    private Long usuarioId;

    // Monto total de la transacción en pesos chilenos
    @Column(nullable = false)
    private Double monto;

    // Método de pago utilizado
    // length = 30 limita el tamaño de la columna en la base de datos
    // Valores válidos: TARJETA_CREDITO, JUNAEB, SUBSIDIO_EMPRESA, EFECTIVO
    @Column(nullable = false, length = 30)
    private String metodoPago;

    // Fecha y hora exacta en que se procesó el pago
    // LocalDateTime almacena fecha y hora sin zona horaria
    // Se asigna automáticamente en el Service con LocalDateTime.now()
    @Column(nullable = false)
    private LocalDateTime fechaPago;

    // Estado actual de la transacción
    // length = 20 limita el tamaño de la columna en la base de datos
    // Valores válidos: PENDIENTE, APROBADO, RECHAZADO
    @Column(nullable = false, length = 20)
    private String estadoPago;
}