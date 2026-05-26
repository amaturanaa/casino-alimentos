package com.casino.mspagos.service;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Transaccion
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase TransaccionServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface TransaccionService {

    // Procesa un nuevo pago para un pedido
    // Valida que el método de pago sea válido
    // Lanza RuntimeException si ya existe un pago para ese pedido
    TransaccionResponseDTO procesarPago(TransaccionRequestDTO dto);

    // Obtiene una transacción por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    TransaccionResponseDTO obtenerPorId(Long id);

    // Obtiene la transacción asociada a un pedido específico
    // Relación 1 a 1: cada pedido tiene como máximo un pago
    TransaccionResponseDTO obtenerPorPedido(Long pedidoId);

    // Lista transacciones filtradas por usuario
    // usuarioId es referencia a ms-usuarios sin FK física
    List<TransaccionResponseDTO> listarPorUsuario(Long usuarioId);

    // Lista transacciones filtradas por estado de pago
    // Ejemplo: listarPorEstado("APROBADO")
    List<TransaccionResponseDTO> listarPorEstado(String estado);

    // Lista transacciones filtradas por método de pago
    // Ejemplo: listarPorMetodo("JUNAEB")
    List<TransaccionResponseDTO> listarPorMetodo(String metodoPago);

    // Cambia el estado de una transacción existente
    // Estados válidos: PENDIENTE, APROBADO, RECHAZADO
    // Lanza RuntimeException si el estado no es válido
    TransaccionResponseDTO cambiarEstado(Long id, String estado);

    // Lista todas las transacciones del sistema sin filtros
    List<TransaccionResponseDTO> listar();
}