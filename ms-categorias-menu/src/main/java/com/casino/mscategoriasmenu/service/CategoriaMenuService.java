package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;

import java.util.List;

public interface CategoriaMenuService {

    CategoriaMenuResponseDTO crear(CategoriaMenuRequestDTO request);

    List<CategoriaMenuResponseDTO> listar();

    CategoriaMenuResponseDTO obtenerPorId(Long id);

    CategoriaMenuResponseDTO cambiarEstado(Long id, Boolean estado);

    List<CategoriaMenuResponseDTO> listarPorEstado(Boolean estado);
}
