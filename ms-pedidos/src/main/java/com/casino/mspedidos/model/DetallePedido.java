package com.casino.mspedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "detalle_pedido" en db_pedidos
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Cada detalle representa un plato dentro de un pedido con su cantidad y subtotal
@Entity
@Table(name = "detalle_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePedido;

    // Relación ManyToOne con Pedido dentro del mismo microservicio
    // Muchos detalles pueden pertenecer al mismo pedido
    // @JoinColumn define la columna FK en la tabla detalle_pedido
    // nullable = false indica que todo detalle debe tener un pedido asociado
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Referencia al plato en ms-menu solicitado en este detalle
    // No es FK física — cada microservicio maneja su propia base de datos
    @Column(nullable = false)
    private Long platoId;

    // Cantidad de unidades del plato solicitadas
    @Column(nullable = false)
    private Integer cantidad;

    // Subtotal de este detalle en pesos chilenos
    // Calculado como precio unitario * cantidad
    @Column(nullable = false)
    private Double subTotal;
}