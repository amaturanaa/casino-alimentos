package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoRequestDTO;
import com.casino.mssucursales.dto.EmpleadoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Empleado en ms-sucursales
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase EmpleadoServiceImpl
public interface EmpleadoService {

    // Crea un nuevo empleado de sucursal validando RUN único
    // Lanza RuntimeException si el RUN ya existe — capturada por GlobalExceptionHandler
    EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto);

    // Lista todos los empleados de sucursal del sistema sin filtros
    List<EmpleadoResponseDTO> listarEmpleados();
}