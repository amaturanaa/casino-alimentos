package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import java.util.List;

// Interfaz del Service para la relación EmpleadoSede
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase EmpleadoSedeServiceImpl
// Gestiona la relación muchos a muchos entre Empleado y SedeCasino
public interface EmpleadoSedeService {

    // Asigna un empleado a una sede específica
    // Crea la relación en la tabla intermedia empleado_sede
    // Lanza RuntimeException si el empleado ya está asignado a esa sede
    EmpleadoSedeResponseDTO asignarEmpleadoASede(EmpleadoSedeRequestDTO request);

    // Elimina la asignación de un empleado a una sede
    // Usa la clave compuesta (idEmpleado + idSedeCasino) para identificar el registro
    void eliminarAsignacion(Long idEmpleado, Long idSedeCasino);

    // Lista todas las asignaciones de empleados para una sede específica
    // Útil para ver qué empleados trabajan en una sede
    List<EmpleadoSedeResponseDTO> listarPorSede(Long idSedeCasino);

    // Lista todas las asignaciones de sedes para un empleado específico
    // Útil para ver en qué sedes trabaja un empleado
    List<EmpleadoSedeResponseDTO> listarPorEmpleado(Long idEmpleado);
}