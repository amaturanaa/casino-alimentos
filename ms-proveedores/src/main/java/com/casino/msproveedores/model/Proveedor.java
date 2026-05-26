package com.casino.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "proveedor" en db_proveedores
// @Entity indica que esta clase es una entidad persistente en la base de datos
// Registra los proveedores que abastecen a las sedes del casino
@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    // RUT del proveedor sin dígito verificador
    // unique = true garantiza que no se repita el RUT en la base de datos
    // length = 8 limita el tamaño de la columna
    @Column(nullable = false, unique = true, length = 8)
    private String rutProveedor;

    // Dígito verificador del RUT — máximo 1 carácter (número o K)
    @Column(nullable = false, length = 1)
    private String dvRunProveedor;

    // Razón social de la empresa proveedora
    // length = 100 limita el tamaño de la columna
    @Column(nullable = false, length = 100)
    private String razonSocial;

    // Email de contacto del proveedor
    // length = 100 limita el tamaño de la columna
    @Column(nullable = false, length = 100)
    private String emailProveedor;

    // Número de teléfono del proveedor
    // length = 15 limita el tamaño de la columna
    @Column(nullable = false, length = 15)
    private String telefono;

    // Estado del proveedor en el sistema
    // true = activo (puede recibir órdenes de compra)
    // false = inactivo (baja lógica sin eliminar el registro)
    // Valor por defecto: true (nuevo proveedor siempre activo)
    @Column(nullable = false)
    private Boolean activo = true;
}