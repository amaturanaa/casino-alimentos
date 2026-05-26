package com.casino.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// Entidad JPA que representa la tabla "orden_compra" en db_proveedores
// @Entity indica que esta clase es una entidad persistente en la base de datos
// Registra cada orden de compra realizada a un proveedor para una sede
@Entity
@Table(name = "orden_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompra {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdenCompra;

    // Relación ManyToOne con Proveedor dentro del mismo microservicio
    // Muchas órdenes pueden pertenecer al mismo proveedor
    // @JoinColumn define la columna FK en la tabla orden_compra
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Referencia a la sede en ms-sucursales que realiza la compra
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de sede se hace via Feign Client en el Service al crear
    @Column(nullable = false)
    private Long sedeId;

    // Fecha y hora exacta en que se creó la orden de compra
    // Se asigna automáticamente en el Service con LocalDateTime.now()
    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    // Estado actual de la orden de compra
    // length = 20 limita el tamaño de la columna en la base de datos
    // Valores válidos: PENDIENTE, RECIBIDA, CANCELADA
    @Column(nullable = false, length = 20)
    private String estado;

    // Costo total de la orden en pesos chilenos
    // Calculado como suma de subtotales de todos los detalles
    @Column(nullable = false)
    private Double costoTotal;
}