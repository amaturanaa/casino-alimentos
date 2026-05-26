package com.casino.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "detalle_orden_compra" en db_proveedores
// @Entity indica que esta clase es una entidad persistente en la base de datos
// Cada detalle representa un producto dentro de una orden de compra
@Entity
@Table(name = "detalle_orden_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenCompra {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    // Relación ManyToOne con OrdenCompra dentro del mismo microservicio
    // Muchos detalles pueden pertenecer a la misma orden de compra
    // @JoinColumn define la columna FK en la tabla detalle_orden_compra
    @ManyToOne
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    // Nombre del producto a comprar
    // length = 100 limita el tamaño de la columna en la base de datos
    @Column(nullable = false, length = 100)
    private String nombreProducto;

    // Cantidad de unidades del producto a comprar
    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario del producto en pesos chilenos
    @Column(nullable = false)
    private Double precioUnitario;

    // Subtotal calculado como cantidad * precioUnitario
    @Column(nullable = false)
    private Double subTotal;
}