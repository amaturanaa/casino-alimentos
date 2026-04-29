package com.casino.msusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false, unique = true, length = 12)
    private String rut_usuario;

    @Column(nullable = false, length = 50)
    private String pnombre_usuario;

    @Column(length = 50)
    private String snombre_usuario;

    @Column(nullable = false, length = 50)
    private String appaterno_usuario;

    @Column(length = 50)
    private String apmaterno_usuario;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}
