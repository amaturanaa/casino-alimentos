package com.casino.msmenu.service;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ProgramacionDiariaService {

    ProgramacionDiariaResponseDTO crear(ProgramacionDiariaRequestDTO dto);

    List<ProgramacionDiariaResponseDTO> listarPorFecha(LocalDate fecha);

    List<ProgramacionDiariaResponseDTO> listarPorSede(Long sedeId);

    List<ProgramacionDiariaResponseDTO> listarPorFechaYSede(LocalDate fecha, Long sedeId);

    ProgramacionDiariaResponseDTO descontarRacion(Long id);
}
