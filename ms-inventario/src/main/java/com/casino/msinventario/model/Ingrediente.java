package com.casino.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "ingrediente" en db_inventario
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Cada ingrediente está asociado a una sede del casino
@Entity
@Table(name = "ingrediente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingrediente {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;

    // Nombre descriptivo del ingrediente
    // length = 100 limita el tamaño de la columna en la base de datos
    // Ejemplo: "Arroz", "Aceite", "Sal", "Pollo"
    @Column(nullable = false, length = 100)
    private String nombreIngrediente;

    // Referencia a la sede en ms-sucursales donde se almacena el ingrediente
    // No es FK física — cada microservicio maneja su propia base de datos
    // La validación de sede se hace via Feign Client en el Service al crear
    @Column(nullable = false)
    private Long sedeId;

    // Unidad de medida del ingrediente
    // length = 20 limita el tamaño de la columna en la base de datos
    // Ejemplo: "kg", "litros", "unidades", "gramos"
    @Column(nullable = false, length = 20)
    private String unidadMedida;

    // Cantidad actual disponible del ingrediente en bodega
    // Se actualiza automáticamente al registrar movimientos de stock
    @Column(nullable = false)
    private Double stockActual;

    // Cantidad mínima requerida del ingrediente antes de generar alerta
    // Si stockActual <= stockMinimo el ingrediente se marca como stock bajo
    @Column(nullable = false)
    private Double stockMinimo;
}