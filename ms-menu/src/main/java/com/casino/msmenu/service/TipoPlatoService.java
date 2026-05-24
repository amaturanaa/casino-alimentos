package com.casino.msmenu.service;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad TipoPlato
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase TipoPlatoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface TipoPlatoService {

    // Crea un nuevo tipo de plato validando nombre único
    // Lanza RuntimeException si el nombre ya existe — capturada por GlobalExceptionHandler
    // Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
    TipoPlatoResponseDTO crear(TipoPlatoRequestDTO dto);

    // Lista todos los tipos de plato del sistema sin filtros
    List<TipoPlatoResponseDTO> listar();

    // Obtiene un tipo de plato por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    TipoPlatoResponseDTO obtenerPorId(Long id);
}