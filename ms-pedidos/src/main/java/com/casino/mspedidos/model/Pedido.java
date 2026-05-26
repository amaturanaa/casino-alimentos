package com.casino.mspedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// Entidad JPA que representa la tabla "pedido" en db_pedidos
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Registra cada pedido realizado por un usuario en una sede del casino
@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    // Referencia al usuario en ms-usuarios que realizó el pedido
    // No es FK física — cada microservicio maneja su propia base de datos
    @Column(nullable = false)
    private Long usuarioId;

    // Referencia a la sede en ms-sucursales donde se realizó el pedido
    // No es FK física — cada microservicio maneja su propia base de datos
    @Column(nullable = false)
    private Long sedeId;

    // Fecha y hora exacta en que se creó el pedido
    // LocalDateTime almacena fecha y hora sin zona horaria
    // Se asigna automáticamente en el Service con LocalDateTime.now()
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Estado actual del pedido en el flujo de preparación
    // length = 30 limita el tamaño de la columna en la base de datos
    // Flujo: RECIBIDO → EN_PREPARACION → LISTO_RETIRO → ENTREGADO
    @Column(nullable = false, length = 30)
    private String estado;

    // Total del pedido en pesos chilenos
    // Calculado como la suma de todos los subtotales de los detalles
    @Column(nullable = false)
    private Double totalPedido;

    // Relación OneToMany con DetallePedido dentro del mismo microservicio
    // Un pedido puede tener múltiples detalles (uno por cada plato)
    // cascade = ALL propaga operaciones de persistencia a los detalles
    // fetch = LAZY carga los detalles solo cuando se accede a ellos
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;
}