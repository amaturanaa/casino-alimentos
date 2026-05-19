package com.casino.msreservas.service;

import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import java.util.List;

public interface ReservaService {

    ReservaResponseDTO crear(ReservaRequestDTO dto);

    ReservaResponseDTO obtenerPorId(Long id);

    List<ReservaResponseDTO> listarPorUsuario(Long usuarioId);

    List<ReservaResponseDTO> listarPorTurno(Long turnoId);

    List<ReservaResponseDTO> listarPorSede(Long sedeId);

    List<ReservaResponseDTO> listarPorEstado(String estado);

    ReservaResponseDTO cancelar(Long id);

    List<ReservaResponseDTO> listar();
}