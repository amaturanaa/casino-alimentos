package com.casino.msempleados.service;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import java.util.List;

public interface EmpleadoService {
    EmpleadoResponseDTO crear(EmpleadoRequestDTO dto);
    EmpleadoResponseDTO obtenerPorId(Long id);
    EmpleadoResponseDTO obtenerPorRut(String rut);
    List<EmpleadoResponseDTO> listar();
    List<EmpleadoResponseDTO> listarActivos();
    List<EmpleadoResponseDTO> listarPorCargo(String cargo);
    EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto);
    EmpleadoResponseDTO cambiarEstado(Long id, Boolean activo);
}