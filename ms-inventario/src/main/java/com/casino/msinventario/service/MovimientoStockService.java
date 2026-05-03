package com.casino.msinventario.service;

import com.casino.msinventario.dto.MovimientoStockRequestDTO;
import com.casino.msinventario.dto.MovimientoStockResponseDTO;

import java.util.List;

public interface MovimientoStockService {

    MovimientoStockResponseDTO registrar(MovimientoStockRequestDTO dto);

    List<MovimientoStockResponseDTO> listar();

    List<MovimientoStockResponseDTO> listarPorIngrediente(Long ingredienteId);

    List<MovimientoStockResponseDTO> listarPorTipo(Long tipoMovimientoId);
}
