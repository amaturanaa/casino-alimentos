package com.casino.msinventario.service;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad TipoMovimiento
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase TipoMovimientoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface TipoMovimientoService {

    // Crea un nuevo tipo de movimiento validando nombre único
    // Lanza RuntimeException si el nombre ya existe — capturada por GlobalExceptionHandler
    // Ejemplo: "ENTRADA", "SALIDA", "MERMA"
    TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto);

    // Lista todos los tipos de movimiento del sistema sin filtros
    List<TipoMovimientoResponseDTO> listar();

    // Obtiene un tipo de movimiento por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    TipoMovimientoResponseDTO obtenerPorId(Long id);
}