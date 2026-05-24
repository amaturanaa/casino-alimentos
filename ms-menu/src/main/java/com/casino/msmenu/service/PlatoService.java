package com.casino.msmenu.service;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Plato
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase PlatoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface PlatoService {

    // Crea un nuevo plato verificando que la categoría esté activa via Feign
    // Lanza RuntimeException si la categoría no existe o no está activa
    PlatoResponseDTO crear(PlatoRequestDTO dto);

    // Lista todos los platos del sistema sin filtros
    List<PlatoResponseDTO> listar();

    // Obtiene un plato por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    PlatoResponseDTO obtenerPorId(Long id);

    // Cambia la disponibilidad de un plato
    // true = disponible (puede ser incluido en pedidos)
    // false = no disponible (no puede ser incluido en pedidos)
    PlatoResponseDTO cambiarDisponibilidad(Long id, Boolean disponible);

    // Lista platos filtrados por tipo de plato
    // Ejemplo: listarPorTipo(1) retorna todos los platos de tipo "Plato de Fondo"
    List<PlatoResponseDTO> listarPorTipo(Long tipoPlatoId);

    // Lista platos filtrados por categoría de menú
    // categoriaId es referencia a ms-categorias-menu sin FK física
    List<PlatoResponseDTO> listarPorCategoria(Long categoriaId);
}