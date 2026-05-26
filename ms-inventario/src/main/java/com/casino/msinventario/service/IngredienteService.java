package com.casino.msinventario.service;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Ingrediente
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase IngredienteServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface IngredienteService {

    // Crea un nuevo ingrediente validando que la sede esté operativa via Feign
    // Lanza RuntimeException si la sede no existe o no está operativa
    IngredienteResponseDTO crear(IngredienteRequestDTO dto);

    // Obtiene un ingrediente por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    IngredienteResponseDTO obtenerPorId(Long id);

    // Lista todos los ingredientes del sistema sin filtros
    List<IngredienteResponseDTO> listar();

    // Lista ingredientes filtrados por sede
    // sedeId es referencia a ms-sucursales sin FK física
    List<IngredienteResponseDTO> listarPorSede(Long sedeId);

    // Lista ingredientes con stock bajo en todas las sedes
    // Un ingrediente tiene stock bajo cuando stockActual <= stockMinimo
    List<IngredienteResponseDTO> listarStockBajo();

    // Lista ingredientes con stock bajo filtrados por sede específica
    // Combinación de filtro por sede y condición de stock bajo
    List<IngredienteResponseDTO> listarStockBajoPorSede(Long sedeId);

    // Actualiza todos los datos de un ingrediente existente
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto);
}