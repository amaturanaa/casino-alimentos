package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "platos" en db_menu
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Cada plato pertenece a un tipo de plato y a una categoría de menú
@Entity
@Table(name = "platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    // Nombre descriptivo del plato
    // length = 100 limita el tamaño de la columna en la base de datos
    // Ejemplo: "Pollo a la plancha", "Cazuela de vacuno", "Ensalada mixta"
    @Column(nullable = false, length = 100)
    private String nombrePlato;

    // Descripción detallada del plato — campo opcional puede ser null
    // length = 255 limita el tamaño de la columna en la base de datos
    @Column(length = 255)
    private String descripcionPlato;

    // Precio referencial del plato en pesos chilenos
    // nullable = false indica que el campo es obligatorio en la base de datos
    @Column(nullable = false)
    private Double precioReferencial;

    // Relación ManyToOne con TipoPlato dentro del mismo microservicio
    // Muchos platos pueden pertenecer al mismo tipo de plato
    // @JoinColumn define la columna FK en la tabla platos
    // nullable = false indica que todo plato debe tener un tipo asociado
    @ManyToOne
    @JoinColumn(name = "tipo_plato_id", nullable = false)
    private TipoPlato tipoPlato;

    // Referencia a la categoría en ms-categorias-menu
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de categoría se hace via Feign Client en el Service al crear
    @Column(nullable = false)
    private Long categoriaId;

    // Estado de disponibilidad del plato en el sistema
    // true = disponible (puede ser incluido en pedidos y programaciones)
    // false = no disponible (no puede ser incluido en pedidos)
    // Valor por defecto: true (nuevo plato siempre disponible)
    @Column(nullable = false)
    private Boolean disponible = true;
}