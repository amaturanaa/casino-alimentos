package com.casino.msinventario.repository;

import com.casino.msinventario.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para acceso a datos de la entidad TipoMovimiento
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Long> {

    // Derived Query — verifica si existe un tipo de movimiento con ese nombre
    // Equivale a: SELECT COUNT(*) > 0 FROM tipo_movimiento WHERE nombre_tipo_movimiento = ?
    // Usado en el Service para validar nombre único antes de crear un tipo de movimiento
    // Ejemplo: evita duplicar "ENTRADA", "SALIDA" o "MERMA"
    boolean existsByNombreTipoMovimiento(String nombre);
}