package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;

import java.util.List;


public interface SedeCasinoService {

    SedeCasinoResponseDTO crearSede(SedeCasinoRequestDTO dto);

    List<SedeCasinoResponseDTO> listarSedes();

    SedeCasinoResponseDTO obtenerPorId(Long id);

    SedeCasinoResponseDTO cambiarEstado(Long id, Boolean estado);
}
