package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoRequestDTO;
import com.casino.mssucursales.dto.EmpleadoResponseDTO;

import java.util.List;

public interface EmpleadoService {

    EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto);

    List<EmpleadoResponseDTO> listarEmpleados();
}
