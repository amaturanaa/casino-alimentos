package com.casino.mscategoriasmenu.service;


import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;

import java.util.List;

public interface EtiquetaNutricionalService {

    EtiquetaNutricionalResponseDTO crear(EtiquetaNutricionalRequestDTO dto);

    EtiquetaNutricionalResponseDTO obtenerPorCategoria(Long categoriaId);

    EtiquetaNutricionalResponseDTO actualizar(Long id, EtiquetaNutricionalRequestDTO dto);

    List<EtiquetaNutricionalResponseDTO> listar();
}
