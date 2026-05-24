package com.casino.mscategoriasmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "etiqueta_nutricional" en db_categorias_menu
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Relación 1 a 1 con CategoriaMenu — cada categoría tiene una sola etiqueta nutricional
@Entity
@Table(name = "etiqueta_nutricional")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaNutricional {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtiquetaNutricional;

    // Relación OneToOne con CategoriaMenu dentro del mismo microservicio
    // Una categoría tiene como máximo una etiqueta nutricional
    // @JoinColumn define la columna FK en la tabla etiqueta_nutricional
    // unique = true garantiza la relación 1 a 1 en la base de datos
    // nullable = false indica que toda etiqueta debe tener una categoría asociada
    @OneToOne
    @JoinColumn(name = "categoria_id", nullable = false, unique = true)
    private CategoriaMenu categoriaMenu;

    // Cantidad de calorías por porción del plato
    // nullable = false indica que el campo es obligatorio en la base de datos
    @Column(nullable = false)
    private Integer calorias;

    // Cantidad de proteínas en gramos por porción
    @Column(nullable = false)
    private Double proteinas;

    // Cantidad de carbohidratos en gramos por porción
    @Column(nullable = false)
    private Double carbohidratos;

    // Cantidad de grasas en gramos por porción
    @Column(nullable = false)
    private Double grasas;

    // Indica si el plato es apto para dieta vegetariana
    // Valor por defecto: false (no es vegetariano)
    @Column(nullable = false)
    private Boolean esVegetariano = false;

    // Indica si el plato es apto para dieta vegana
    // Valor por defecto: false (no es vegano)
    @Column(nullable = false)
    private Boolean esVegano = false;

    // Indica si el plato contiene gluten
    // Valor por defecto: true (contiene gluten)
    // false = libre de gluten (apto para celíacos)
    @Column(nullable = false)
    private Boolean contieneGluten = true;
}