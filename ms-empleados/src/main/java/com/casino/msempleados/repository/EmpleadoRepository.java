package com.casino.msempleados.repository;

import com.casino.msempleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad Empleado
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Derived Query — Spring genera el SQL automáticamente desde el nombre del método
    // Equivale a: SELECT COUNT(*) > 0 FROM empleado WHERE rut_empleado = ?
    // Usado para validar RUT único antes de crear un empleado
    boolean existsByRutEmpleado(String rutEmpleado);

    // Derived Query — busca un empleado por su RUT
    // Retorna Optional para manejar el caso de empleado no encontrado
    // Equivale a: SELECT * FROM empleado WHERE rut_empleado = ?
    Optional<Empleado> findByRutEmpleado(String rutEmpleado);

    // Derived Query — filtra empleados por estado activo
    // Equivale a: SELECT * FROM empleado WHERE activo = ?
    // Usado para listar solo empleados activos o inactivos
    List<Empleado> findByActivo(Boolean activo);

    // Derived Query — filtra empleados por cargo
    // Equivale a: SELECT * FROM empleado WHERE cargo = ?
    // Ejemplo: findByCargo("Cocinero") retorna todos los cocineros
    List<Empleado> findByCargo(String cargo);
}