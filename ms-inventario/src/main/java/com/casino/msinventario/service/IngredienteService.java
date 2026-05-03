package com.casino.msinventario.service;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;

import java.util.List;

public interface IngredienteService {

    IngredienteResponseDTO crear(IngredienteRequestDTO dto);

    IngredienteResponseDTO obtenerPorId(Long id);

    List<IngredienteResponseDTO> listar();

    List<IngredienteResponseDTO> listarPorSede(Long sedeId);

    List<IngredienteResponseDTO> listarStockBajo();

    List<IngredienteResponseDTO> listarStockBajoPorSede(Long sedeId);

    IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto);
}
