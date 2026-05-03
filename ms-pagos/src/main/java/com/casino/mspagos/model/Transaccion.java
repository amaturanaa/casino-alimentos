package com.casino.mspagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    // Referencia a ms-pedidos
    @Column(nullable = false)
    private Long pedidoId;

    // Referencia a ms-usuarios
    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 30)
    private String metodoPago;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false, length = 20)
    private String estadoPago;
}
