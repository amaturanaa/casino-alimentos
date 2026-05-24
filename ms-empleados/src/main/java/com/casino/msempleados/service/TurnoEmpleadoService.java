package com.casino.msempleados.service;

import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import java.time.LocalDate;
import java.util.List;

// Interfaz del Service para la entidad TurnoEmpleado
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase TurnoEmpleadoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface TurnoEmpleadoService {

    // Crea un nuevo turno para un empleado
    // Verifica que el empleado esté activo y la sede operativa via Feign
    TurnoEmpleadoResponseDTO crear(TurnoEmpleadoRequestDTO dto);

    // Obtiene un turno por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    TurnoEmpleadoResponseDTO obtenerPorId(Long id);

    // Lista todos los turnos del sistema sin filtros
    List<TurnoEmpleadoResponseDTO> listar();

    // Lista todos los turnos asignados a un empleado específico
    List<TurnoEmpleadoResponseDTO> listarPorEmpleado(Long idEmpleado);

    // Lista todos los turnos de una sede específica
    // sedeId es referencia a ms-sucursales
    List<TurnoEmpleadoResponseDTO> listarPorSede(Long sedeId);

    // Lista todos los turnos de una fecha específica
    // fecha en formato LocalDate: yyyy-MM-dd
    List<TurnoEmpleadoResponseDTO> listarPorFecha(LocalDate fecha);

    // Lista turnos filtrados por sede y fecha simultáneamente
    // Útil para ver el personal de una sede en un día específico
    List<TurnoEmpleadoResponseDTO> listarPorSedeYFecha(Long sedeId, LocalDate fecha);

    // Lista turnos filtrados por tipo de turno
    // Ejemplo: listarPorTipo("MAÑANA")
    List<TurnoEmpleadoResponseDTO> listarPorTipo(String tipoTurno);

    // Elimina un turno por su id
    // Retorna void — el controller responde con 204 No Content
    void eliminar(Long id);
}