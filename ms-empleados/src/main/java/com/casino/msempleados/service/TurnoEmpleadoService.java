package com.casino.msempleados.service;

import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface TurnoEmpleadoService {
    TurnoEmpleadoResponseDTO crear(TurnoEmpleadoRequestDTO dto);
    TurnoEmpleadoResponseDTO obtenerPorId(Long id);
    List<TurnoEmpleadoResponseDTO> listar();
    List<TurnoEmpleadoResponseDTO> listarPorEmpleado(Long idEmpleado);
    List<TurnoEmpleadoResponseDTO> listarPorSede(Long sedeId);
    List<TurnoEmpleadoResponseDTO> listarPorFecha(LocalDate fecha);
    List<TurnoEmpleadoResponseDTO> listarPorSedeYFecha(Long sedeId, LocalDate fecha);
    List<TurnoEmpleadoResponseDTO> listarPorTipo(String tipoTurno);
    void eliminar(Long id);
}