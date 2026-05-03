package com.casino.msinventario.service;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;

import java.util.List;

public interface TipoMovimientoService {

    TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto);

    List<TipoMovimientoResponseDTO> listar();

    TipoMovimientoResponseDTO obtenerPorId(Long id);
}
