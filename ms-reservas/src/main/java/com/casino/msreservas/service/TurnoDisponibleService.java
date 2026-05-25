package com.casino.msreservas.service;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import java.time.LocalDate;
import java.util.List;

// Interfaz del Service para la entidad TurnoDisponible
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase TurnoDisponibleServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface TurnoDisponibleService {

    // Crea un nuevo turno disponible para una sede y fecha
    // Los cuposRestantes se inicializan igual a la capacidad total
    TurnoDisponibleResponseDTO crear(TurnoDisponibleRequestDTO dto);

    // Lista todos los turnos del sistema sin filtros
    List<TurnoDisponibleResponseDTO> listar();

    // Obtiene un turno por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    TurnoDisponibleResponseDTO obtenerPorId(Long id);

    // Lista turnos filtrados por sede
    // sedeId es referencia a ms-sucursales sin FK física
    List<TurnoDisponibleResponseDTO> listarPorSede(Long sedeId);

    // Lista turnos filtrados por fecha específica
    // fecha en formato LocalDate: yyyy-MM-dd
    List<TurnoDisponibleResponseDTO> listarPorFecha(LocalDate fecha);

    // Lista turnos con cupos disponibles filtrados por sede y fecha
    // Solo retorna turnos donde cuposRestantes > 0
    // Endpoint principal para que usuarios vean qué turnos pueden reservar
    List<TurnoDisponibleResponseDTO> listarDisponiblesPorSedeYFecha(Long sedeId, LocalDate fecha);
}