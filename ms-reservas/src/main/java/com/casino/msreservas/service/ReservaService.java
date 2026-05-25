package com.casino.msreservas.service;

import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Reserva
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase ReservaServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface ReservaService {

    // Crea una nueva reserva verificando usuario activo via Feign con ms-usuarios
    // y sede operativa via Feign con ms-sucursales
    // Lanza RuntimeException si el usuario está inactivo, sede no operativa,
    // no hay cupos disponibles o el usuario ya tiene reserva activa en ese turno
    ReservaResponseDTO crear(ReservaRequestDTO dto);

    // Obtiene una reserva por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    ReservaResponseDTO obtenerPorId(Long id);

    // Lista todas las reservas de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física
    List<ReservaResponseDTO> listarPorUsuario(Long usuarioId);

    // Lista todas las reservas de un turno específico
    List<ReservaResponseDTO> listarPorTurno(Long turnoId);

    // Lista todas las reservas de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física
    List<ReservaResponseDTO> listarPorSede(Long sedeId);

    // Lista reservas filtradas por estado
    // Ejemplo: listarPorEstado("ACTIVA") o listarPorEstado("CANCELADA")
    List<ReservaResponseDTO> listarPorEstado(String estado);

    // Cancela una reserva existente y devuelve el cupo al turno
    // Lanza RuntimeException si la reserva ya está cancelada
    ReservaResponseDTO cancelar(Long id);

    // Lista todas las reservas del sistema sin filtros
    List<ReservaResponseDTO> listar();
}