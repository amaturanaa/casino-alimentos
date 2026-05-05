package com.casino.msreservas.repository;

import com.casino.msreservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByTurno_IdTurno(Long turnoId);
    List<Reserva> findByEstado(String estado);
    List<Reserva> findBySedeId(Long sedeId);
    boolean existsByUsuarioIdAndTurno_IdTurnoAndEstado(Long usuarioId, Long turnoId, String estado);
}