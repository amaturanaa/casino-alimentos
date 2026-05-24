package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad EtiquetaNutricional
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase EtiquetaNutricionalServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface EtiquetaNutricionalService {

    // Crea una nueva etiqueta nutricional para una categoría
    // Valida que la categoría exista y no tenga ya una etiqueta asociada
    // Lanza RuntimeException si ya existe — capturada por GlobalExceptionHandler
    EtiquetaNutricionalResponseDTO crear(EtiquetaNutricionalRequestDTO dto);

    // Obtiene la etiqueta nutricional de una categoría específica
    // Relación 1 a 1: cada categoría tiene como máximo una etiqueta
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    EtiquetaNutricionalResponseDTO obtenerPorCategoria(Long categoriaId);

    // Actualiza todos los datos de una etiqueta nutricional existente
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    EtiquetaNutricionalResponseDTO actualizar(Long id, EtiquetaNutricionalRequestDTO dto);

    // Lista todas las etiquetas nutricionales del sistema sin filtros
    List<EtiquetaNutricionalResponseDTO> listar();
}