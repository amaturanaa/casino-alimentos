package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "tipo_plato" en db_menu
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Clasifica los platos del menú del casino por su tipo
// Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
@Entity
@Table(name = "tipo_plato")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPlato {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoPlato;

    // Nombre descriptivo del tipo de plato
    // unique = true garantiza que no se repita el nombre en la base de datos
    // length = 50 limita el tamaño de la columna en la base de datos
    // Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
    @Column(nullable = false, unique = true, length = 50)
    private String nombreTipoPlato;
}