package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad CategoriaMenu
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase CategoriaMenuServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
// Las categorías son consumidas por ms-menu via Feign para validar platos
public interface CategoriaMenuService {

    // Crea una nueva categoría validando nombre único
    // Lanza RuntimeException si el nombre ya existe — capturada por GlobalExceptionHandler
    CategoriaMenuResponseDTO crear(CategoriaMenuRequestDTO request);

    // Lista todas las categorías del sistema sin filtros
    List<CategoriaMenuResponseDTO> listar();

    // Obtiene una categoría por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    CategoriaMenuResponseDTO obtenerPorId(Long id);

    // Cambia el estado activo/inactivo de una categoría
    // Si se desactiva, ms-menu no podrá crear platos con ella via Feign
    CategoriaMenuResponseDTO cambiarEstado(Long id, Boolean estado);

    // Lista categorías filtradas por estado
    // Ejemplo: listarPorEstado(true) retorna solo categorías activas
    List<CategoriaMenuResponseDTO> listarPorEstado(Boolean estado);
}