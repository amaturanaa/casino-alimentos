package com.casino.msusuarios.repository;

import com.casino.msusuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad Usuario
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Derived Query — busca un usuario por su email
    // Equivale a: SELECT * FROM usuario WHERE email = ?
    // Retorna Optional para manejar el caso de usuario no encontrado
    // Usado para buscar por credencial de acceso
    Optional<Usuario> findByEmail(String email);

    // Derived Query — busca un usuario por su RUT
    // Equivale a: SELECT * FROM usuario WHERE rut_usuario = ?
    Optional<Usuario> findByRutUsuario(String rutUsuario);

    // Derived Query — filtra usuarios por estado activo
    // Equivale a: SELECT * FROM usuario WHERE activo = ?
    // Usado para listar solo usuarios activos o inactivos
    List<Usuario> findByActivo(Boolean activo);

    // Derived Query — verifica si existe un usuario con ese email
    // Equivale a: SELECT COUNT(*) > 0 FROM usuario WHERE email = ?
    // Usado para validar email único antes de crear un usuario
    boolean existsByEmail(String email);

    // Derived Query — verifica si existe un usuario con ese RUT
    // Equivale a: SELECT COUNT(*) > 0 FROM usuario WHERE rut_usuario = ?
    // Usado para validar RUT único antes de crear un usuario
    boolean existsByRutUsuario(String rutUsuario);
}