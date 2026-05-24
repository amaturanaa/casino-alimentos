package com.casino.mscategoriasmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "categoria_menu" en db_categorias_menu
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Las categorías son consumidas por ms-menu via Feign para validar platos
@Entity
@Table(name = "categoria_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMenu {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre descriptivo de la categoría
    // unique = true garantiza que no se repita el nombre en la base de datos
    // length = 100 limita el tamaño de la columna en la base de datos
    // Ejemplo: "Almuerzo Completo", "Ensaladas", "Postres", "Bebidas"
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    // Estado de la categoría en el sistema
    // true = activa (ms-menu puede crear platos con esta categoría via Feign)
    // false = inactiva (ms-menu rechaza crear platos con esta categoría)
    @Column(nullable = false)
    private Boolean estado;
}