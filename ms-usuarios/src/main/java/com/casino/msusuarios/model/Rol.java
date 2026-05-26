package com.casino.msusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "rol" en db_usuarios
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Define los roles de acceso del sistema de casino
// Ejemplo: ROLE_ADMIN, ROLE_OPERADOR, ROLE_CLIENTE
@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    // Nombre único del rol en el sistema
    // unique = true garantiza que no se repita el nombre en la base de datos
    // length = 50 limita el tamaño de la columna
    // Ejemplo: ROLE_ADMIN, ROLE_OPERADOR, ROLE_CLIENTE
    @Column(nullable = false, unique = true, length = 50)
    private String nombreRol;

    // Descripción del propósito del rol — campo opcional
    // length = 100 limita el tamaño de la columna
    // Ejemplo: "Administrador del sistema", "Cliente del casino"
    @Column(length = 100)
    private String descripcion;
}