package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad SedeCasino
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase SedeCasinoServiceImpl
// SedeCasino es el recurso principal de ms-sucursales — consumido via Feign por:
// ms-inventario, ms-proveedores, ms-empleados, ms-reservas y ms-pedidos
public interface SedeCasinoService {

    // Crea una nueva sede de casino
    // Estado operativo inicial siempre es true
    SedeCasinoResponseDTO crearSede(SedeCasinoRequestDTO dto);

    // Lista todas las sedes del sistema sin filtros
    List<SedeCasinoResponseDTO> listarSedes();

    // Obtiene una sede por su id
    // Endpoint consumido por múltiples microservicios via Feign
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    SedeCasinoResponseDTO obtenerPorId(Long id);

    // Cambia el estado operativo de una sede
    // true = operativa, false = no operativa
    // Cuando se desactiva, los microservicios via Feign rechazarán crear recursos
    SedeCasinoResponseDTO cambiarEstado(Long id, Boolean estado);

    // Lista sedes filtradas por estado operativo
    // Ejemplo: listarPorEstado(true) retorna solo sedes operativas
    List<SedeCasinoResponseDTO> listarPorEstado(Boolean estado);
}