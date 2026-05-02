package com.casino.msmenu.service;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;

import java.util.List;

public interface PlatoService {

    PlatoResponseDTO crear(PlatoRequestDTO dto);

    List<PlatoResponseDTO> listar();

    PlatoResponseDTO obtenerPorId(Long id);

    PlatoResponseDTO cambiarDisponibilidad(Long id, Boolean disponible);

    List<PlatoResponseDTO> listarPorTipo(Long tipoPlatoId);

    List<PlatoResponseDTO> listarPorCategoria(Long categoriaId);
}
