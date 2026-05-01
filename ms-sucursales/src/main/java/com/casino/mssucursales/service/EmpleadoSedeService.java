package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;

import java.util.List;

public interface EmpleadoSedeService {

    EmpleadoSedeResponseDTO asignarEmpleadoASede(EmpleadoSedeRequestDTO request);

    void eliminarAsignacion(Long idEmpleado, Long idSedeCasino);

    List<EmpleadoSedeResponseDTO> listarPorSede(Long idSedeCasino);

    List<EmpleadoSedeResponseDTO> listarPorEmpleado(Long idEmpleado);
}
