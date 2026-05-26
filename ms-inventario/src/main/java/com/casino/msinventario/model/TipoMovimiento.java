package com.casino.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "tipo_movimiento" en db_inventario
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Clasifica los movimientos de stock del inventario
// Ejemplo: ENTRADA (recepción de mercadería), SALIDA (consumo), MERMA (pérdida)
@Entity
@Table(name = "tipo_movimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMovimiento {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoMovimiento;

    // Nombre descriptivo del tipo de movimiento
    // unique = true garantiza que no se repita el nombre en la base de datos
    // length = 50 limita el tamaño de la columna en la base de datos
    // Ejemplo: "ENTRADA", "SALIDA", "MERMA"
    @Column(nullable = false, unique = true, length = 50)
    private String nombreTipoMovimiento;
}