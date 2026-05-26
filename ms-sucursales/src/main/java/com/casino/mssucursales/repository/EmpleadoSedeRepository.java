package com.casino.mssucursales.repository;

import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad EmpleadoSede
// Usa clave primaria compuesta EmpleadoSedeId como segundo parámetro
// Extiende JpaRepository que provee operaciones CRUD automáticas
public interface EmpleadoSedeRepository extends JpaRepository<EmpleadoSede, EmpleadoSedeId> {

    // Derived Query — filtra asignaciones por sede
    // El guion bajo (_) navega la clave compuesta: id.idSedeCasino
    // Equivale a: SELECT * FROM empleado_sede WHERE idSedeCasino = ?
    // Usado para listar todos los empleados asignados a una sede
    List<EmpleadoSede> findById_IdSedeCasino(Long idSedeCasino);

    // Derived Query — filtra asignaciones por empleado
    // El guion bajo (_) navega la clave compuesta: id.idEmpleado
    // Equivale a: SELECT * FROM empleado_sede WHERE idEmpleado = ?
    // Usado para listar todas las sedes donde trabaja un empleado
    List<EmpleadoSede> findById_IdEmpleado(Long idEmpleado);
}