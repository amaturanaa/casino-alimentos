package com.casino.mssucursales.repository;

import com.casino.mssucursales.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para acceso a datos de la entidad Empleado
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Derived Query — verifica si existe un empleado con ese RUN
    // Equivale a: SELECT COUNT(*) > 0 FROM empleado WHERE run_empleado = ?
    // Usado en el Service para validar RUN único antes de crear un empleado
    boolean existsByRunEmpleado(String runEmpleado);
}