package com.casino.msmenu.repository;

import com.casino.msmenu.model.ProgramacionDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad ProgramacionDiaria
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface ProgramacionDiariaRepository extends JpaRepository<ProgramacionDiaria, Long> {

    // Derived Query — filtra programaciones por fecha específica
    // Equivale a: SELECT * FROM programacion_diaria WHERE fecha = ?
    // Usado para obtener el menú completo de un día determinado
    // Ejemplo: findByFecha(LocalDate.of(2026, 5, 15))
    List<ProgramacionDiaria> findByFecha(LocalDate fecha);

    // Derived Query — filtra programaciones por sede
    // Equivale a: SELECT * FROM programacion_diaria WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    // Usado para obtener todas las programaciones de una sede específica
    List<ProgramacionDiaria> findBySedeId(Long sedeId);

    // Derived Query — filtra programaciones por fecha y sede simultáneamente
    // Equivale a: SELECT * FROM programacion_diaria WHERE fecha = ? AND sede_id = ?
    // Usado para obtener el menú del día de una sede específica
    // Combinación más precisa para consultas de menú diario por sede
    List<ProgramacionDiaria> findByFechaAndSedeId(LocalDate fecha, Long sedeId);
}