package com.casino.msreservas.repository;

import com.casino.msreservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad Reserva
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Derived Query — filtra reservas por usuario
    // Equivale a: SELECT * FROM reserva WHERE usuario_id = ?
    // usuarioId es referencia a ms-usuarios sin FK física
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Derived Query — filtra reservas por turno
    // El guion bajo (_) navega la relación ManyToOne: turno.idTurno
    // Equivale a: SELECT * FROM reserva WHERE turno_id = ?
    List<Reserva> findByTurno_IdTurno(Long turnoId);

    // Derived Query — filtra reservas por estado
    // Equivale a: SELECT * FROM reserva WHERE estado = ?
    // Ejemplo: findByEstado("ACTIVA") o findByEstado("CANCELADA")
    List<Reserva> findByEstado(String estado);

    // Derived Query — filtra reservas por sede
    // Equivale a: SELECT * FROM reserva WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física
    List<Reserva> findBySedeId(Long sedeId);

    // Derived Query — verifica si existe una reserva activa para usuario y turno
    // Equivale a: SELECT COUNT(*) > 0 FROM reserva WHERE usuario_id = ? AND turno_id = ? AND estado = ?
    // Usado en el Service para evitar reservas duplicadas del mismo usuario en el mismo turno
    boolean existsByUsuarioIdAndTurno_IdTurnoAndEstado(Long usuarioId, Long turnoId, String estado);
}