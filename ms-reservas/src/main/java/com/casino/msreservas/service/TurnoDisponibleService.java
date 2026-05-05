package com.casino.msreservas.service;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface TurnoDisponibleService {
    TurnoDisponibleResponseDTO crear(TurnoDisponibleRequestDTO dto);
    List<TurnoDisponibleResponseDTO> listar();
    TurnoDisponibleResponseDTO obtenerPorId(Long id);
    List<TurnoDisponibleResponseDTO> listarPorSede(Long sedeId);
    List<TurnoDisponibleResponseDTO> listarPorFecha(LocalDate fecha);
    List<TurnoDisponibleResponseDTO> listarDisponiblesPorSedeYFecha(Long sedeId, LocalDate fecha);
}