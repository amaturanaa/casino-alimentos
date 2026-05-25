package com.casino.msreservas.repository;

import com.casino.msreservas.model.TurnoDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad TurnoDisponible
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface TurnoDisponibleRepository extends JpaRepository<TurnoDisponible, Long> {

    // Derived Query — filtra turnos por sede
    // Equivale a: SELECT * FROM turno_disponible WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    List<TurnoDisponible> findBySedeId(Long sedeId);

    // Derived Query — filtra turnos por fecha específica
    // Equivale a: SELECT * FROM turno_disponible WHERE fecha = ?
    // Ejemplo: findByFecha(LocalDate.of(2026, 5, 15))
    List<TurnoDisponible> findByFecha(LocalDate fecha);

    // Derived Query — filtra turnos por sede y fecha simultáneamente
    // Equivale a: SELECT * FROM turno_disponible WHERE sede_id = ? AND fecha = ?
    // Usado para obtener todos los turnos de una sede en un día específico
    List<TurnoDisponible> findBySedeIdAndFecha(Long sedeId, LocalDate fecha);

    // Derived Query — filtra turnos con cupos disponibles por sede y fecha
    // GreaterThan genera condición: cupos_restantes > ?
    // Equivale a: SELECT * FROM turno_disponible WHERE sede_id = ? AND fecha = ? AND cupos_restantes > 0
    // Usado para mostrar solo los turnos que aún tienen cupos disponibles para reservar
    List<TurnoDisponible> findBySedeIdAndFechaAndCuposRestantesGreaterThan(
            Long sedeId, LocalDate fecha, Integer cupos);
}