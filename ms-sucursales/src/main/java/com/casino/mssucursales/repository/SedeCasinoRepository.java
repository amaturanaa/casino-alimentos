package com.casino.mssucursales.repository;

import com.casino.mssucursales.model.SedeCasino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad SedeCasino
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
public interface SedeCasinoRepository extends JpaRepository<SedeCasino, Long> {

    // Derived Query — filtra sedes por estado operativo
    // Equivale a: SELECT * FROM sede_casino WHERE estado_operativo = ?
    // Usado para listar solo sedes operativas o no operativas
    // Ejemplo: findByEstadoOperativo(true) retorna todas las sedes activas
    List<SedeCasino> findByEstadoOperativo(Boolean estado);
}