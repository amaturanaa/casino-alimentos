package com.casino.msempleados.repository;

import com.casino.msempleados.model.TurnoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad TurnoEmpleado
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface TurnoEmpleadoRepository extends JpaRepository<TurnoEmpleado, Long> {

    // Derived Query — busca turnos por el id del empleado asociado
    // El guion bajo (_) navega la relación: empleado.idEmpleado
    // Equivale a: SELECT * FROM turno_empleado WHERE empleado_id = ?
    List<TurnoEmpleado> findByEmpleado_IdEmpleado(Long idEmpleado);

    // Derived Query — filtra turnos por sede
    // Equivale a: SELECT * FROM turno_empleado WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física
    List<TurnoEmpleado> findBySedeId(Long sedeId);

    // Derived Query — filtra turnos por fecha específica
    // Equivale a: SELECT * FROM turno_empleado WHERE fecha = ?
    // Ejemplo: findByFecha(LocalDate.of(2026, 5, 15))
    List<TurnoEmpleado> findByFecha(LocalDate fecha);

    // Derived Query — filtra turnos por sede y fecha simultáneamente
    // Equivale a: SELECT * FROM turno_empleado WHERE sede_id = ? AND fecha = ?
    // Útil para obtener todos los turnos de una sede en un día específico
    List<TurnoEmpleado> findBySedeIdAndFecha(Long sedeId, LocalDate fecha);

    // Derived Query — filtra turnos por tipo de turno
    // Equivale a: SELECT * FROM turno_empleado WHERE tipo_turno = ?
    // Ejemplo: findByTipoTurno("MAÑANA") retorna todos los turnos de mañana
    List<TurnoEmpleado> findByTipoTurno(String tipoTurno);
}