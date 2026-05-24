package com.casino.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// Entidad JPA que representa la tabla "movimiento_stock" en db_inventario
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Registra cada entrada, salida o merma de ingredientes en el inventario
@Entity
@Table(name = "movimiento_stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoStock {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    // Relación ManyToOne con Ingrediente dentro del mismo microservicio
    // Un ingrediente puede tener muchos movimientos de stock
    // @JoinColumn define la columna FK en la tabla movimiento_stock
    // nullable = false indica que todo movimiento debe tener un ingrediente asociado
    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;

    // Relación ManyToOne con TipoMovimiento dentro del mismo microservicio
    // Un tipo de movimiento puede estar en muchos movimientos de stock
    // @JoinColumn define la columna FK en la tabla movimiento_stock
    // nullable = false indica que todo movimiento debe tener un tipo asociado
    @ManyToOne
    @JoinColumn(name = "tipo_movimiento_id", nullable = false)
    private TipoMovimiento tipoMovimiento;

    // Cantidad de unidades involucradas en el movimiento
    // ENTRADA: suma al stock del ingrediente
    // SALIDA y MERMA: resta del stock del ingrediente
    @Column(nullable = false)
    private Double cantidad;

    // Fecha y hora exacta en que se registró el movimiento
    // LocalDateTime almacena fecha y hora sin zona horaria
    // Se asigna automáticamente en el Service con LocalDateTime.now()
    @Column(nullable = false)
    private LocalDateTime fechaMovimiento;

    // Descripción del motivo del movimiento
    // length = 100 limita el tamaño de la columna en la base de datos
    // Ejemplo: "Recepción proveedor", "Consumo cocina", "Producto vencido"
    @Column(nullable = false, length = 100)
    private String motivo;
}