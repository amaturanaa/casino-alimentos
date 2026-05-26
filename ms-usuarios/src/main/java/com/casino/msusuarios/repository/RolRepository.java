package com.casino.msusuarios.repository;

import com.casino.msusuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad Rol
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Derived Query — busca un rol por su nombre
    // Equivale a: SELECT * FROM rol WHERE nombre_rol = ?
    // Retorna Optional para manejar el caso de rol no encontrado
    // Usado para buscar roles por nombre sin necesidad del id
    Optional<Rol> findByNombreRol(String nombreRol);
}