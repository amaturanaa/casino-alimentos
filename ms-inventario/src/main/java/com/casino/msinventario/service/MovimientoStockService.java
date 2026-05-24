package com.casino.msinventario.service;

import com.casino.msinventario.dto.MovimientoStockRequestDTO;
import com.casino.msinventario.dto.MovimientoStockResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad MovimientoStock
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase MovimientoStockServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface MovimientoStockService {

    // Registra un nuevo movimiento de stock
    // Actualiza automáticamente el stockActual del ingrediente afectado
    // ENTRADA suma al stock, SALIDA y MERMA restan del stock
    // Lanza RuntimeException si el ingrediente o tipo de movimiento no existe
    MovimientoStockResponseDTO registrar(MovimientoStockRequestDTO dto);

    // Lista todos los movimientos de stock del sistema sin filtros
    List<MovimientoStockResponseDTO> listar();

    // Lista todos los movimientos de un ingrediente específico
    // Útil para ver el historial completo de entradas y salidas de un ingrediente
    List<MovimientoStockResponseDTO> listarPorIngrediente(Long ingredienteId);

    // Lista movimientos filtrados por tipo de movimiento
    // Ejemplo: listarPorTipo(1) retorna solo movimientos de tipo ENTRADA
    List<MovimientoStockResponseDTO> listarPorTipo(Long tipoMovimientoId);
}