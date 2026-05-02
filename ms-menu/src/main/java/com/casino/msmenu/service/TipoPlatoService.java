package com.casino.msmenu.service;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;

import java.util.List;

public interface TipoPlatoService {

    TipoPlatoResponseDTO crear(TipoPlatoRequestDTO dto);

    List<TipoPlatoResponseDTO> listar();

    TipoPlatoResponseDTO obtenerPorId(Long id);
}
