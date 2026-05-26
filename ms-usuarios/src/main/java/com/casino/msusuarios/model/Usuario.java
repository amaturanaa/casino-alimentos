package com.casino.msusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad JPA que representa la tabla "usuario" en db_usuarios
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Gestiona los usuarios del sistema con autenticación BCrypt
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    // RUT del usuario — identificador único nacional
    // unique = true garantiza que no se repita en la base de datos
    // length = 12 limita el tamaño de la columna
    @Column(nullable = false, unique = true, length = 12)
    private String rutUsuario;

    // Primer nombre del usuario — obligatorio
    @Column(nullable = false, length = 50)
    private String pnombreUsuario;

    // Segundo nombre del usuario — opcional (puede ser null)
    @Column(length = 50)
    private String snombreUsuario;

    // Apellido paterno del usuario — obligatorio
    @Column(nullable = false, length = 50)
    private String appaternoUsuario;

    // Apellido materno del usuario — opcional (puede ser null)
    @Column(length = 50)
    private String apmaternoUsuario;

    // Email del usuario — credencial única de acceso al sistema
    // unique = true garantiza que no se repita en la base de datos
    // length = 100 limita el tamaño de la columna
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Contraseña encriptada con BCrypt
    // length = 255 porque BCrypt genera hashes largos
    // NUNCA se retorna al cliente — no está en UsuarioResponseDTO
    @Column(nullable = false, length = 255)
    private String password;

    // Estado del usuario en el sistema
    // true = activo, false = inactivo (baja lógica)
    // Valor por defecto: true (nuevo usuario siempre activo)
    @Column(nullable = false)
    private Boolean activo = true;

    // Relación ManyToOne con Rol dentro del mismo microservicio
    // Muchos usuarios pueden tener el mismo rol
    // @JoinColumn define la columna FK en la tabla usuario
    // nullable = false indica que todo usuario debe tener un rol asignado
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}