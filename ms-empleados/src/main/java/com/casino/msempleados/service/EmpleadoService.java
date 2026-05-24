package com.casino.msempleados.service;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Empleado
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase EmpleadoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface EmpleadoService {

    // Crea un nuevo empleado validando RUT único y datos requeridos
    // Verifica que el empleado no exista antes de persistir
    EmpleadoResponseDTO crear(EmpleadoRequestDTO dto);

    // Obtiene un empleado por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    EmpleadoResponseDTO obtenerPorId(Long id);

    // Obtiene un empleado por su RUT
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    EmpleadoResponseDTO obtenerPorRut(String rut);

    // Lista todos los empleados del sistema sin filtros
    List<EmpleadoResponseDTO> listar();

    // Lista solo empleados con estado activo = true
    List<EmpleadoResponseDTO> listarActivos();

    // Lista empleados filtrados por cargo
    // Ejemplo: listarPorCargo("Cocinero")
    List<EmpleadoResponseDTO> listarPorCargo(String cargo);

    // Actualiza los datos de un empleado existente
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto);

    // Cambia el estado activo/inactivo de un empleado (baja lógica)
    // No elimina el registro de la base de datos
    EmpleadoResponseDTO cambiarEstado(Long id, Boolean activo);
}