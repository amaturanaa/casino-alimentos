package com.casino.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @Column(nullable = false, unique = true, length = 8)
    private String rutProveedor;

    @Column(nullable = false, length = 1)
    private String dvRunProveedor;

    @Column(nullable = false, length = 100)
    private String razonSocial;

    @Column(nullable = false, length = 100)
    private String emailProveedor;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

}
