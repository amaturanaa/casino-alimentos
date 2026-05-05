package com.casino.msempleados.repository;

import com.casino.msempleados.model.TurnoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoEmpleadoRepository extends JpaRepository<TurnoEmpleado, Long> {
    List<TurnoEmpleado> findByEmpleado_IdEmpleado(Long idEmpleado);
    List<TurnoEmpleado> findBySedeId(Long sedeId);
    List<TurnoEmpleado> findByFecha(LocalDate fecha);
    List<TurnoEmpleado> findBySedeIdAndFecha(Long sedeId, LocalDate fecha);
    List<TurnoEmpleado> findByTipoTurno(String tipoTurno);
}