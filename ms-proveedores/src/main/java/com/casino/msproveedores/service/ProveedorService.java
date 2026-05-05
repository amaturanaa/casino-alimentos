package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorService {

    ProveedorResponseDTO crear(ProveedorRequestDTO dto);

    ProveedorResponseDTO obtenerPorId(Long id);

    List<ProveedorResponseDTO> listar();

    List<ProveedorResponseDTO> listarActivos();

    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto);

    ProveedorResponseDTO cambiarEstado(Long id, Boolean activo);
}
