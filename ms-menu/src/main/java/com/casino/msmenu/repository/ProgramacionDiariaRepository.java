package com.casino.msmenu.repository;

import com.casino.msmenu.model.ProgramacionDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgramacionDiariaRepository extends JpaRepository<ProgramacionDiaria, Long> {

    List<ProgramacionDiaria> findByFecha(LocalDate fecha);

    List<ProgramacionDiaria> findBySedeId(Long sedeId);

    List<ProgramacionDiaria> findByFechaAndSedeId(LocalDate fecha, Long sedeId);
}
