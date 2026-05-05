package com.casino.msreservas.repository;

import com.casino.msreservas.model.TurnoDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoDisponibleRepository extends JpaRepository<TurnoDisponible, Long> {
    List<TurnoDisponible> findBySedeId(Long sedeId);
    List<TurnoDisponible> findByFecha(LocalDate fecha);
    List<TurnoDisponible> findBySedeIdAndFecha(Long sedeId, LocalDate fecha);
    List<TurnoDisponible> findBySedeIdAndFechaAndCuposRestantesGreaterThan(Long sedeId, LocalDate fecha, Integer cupos);
}