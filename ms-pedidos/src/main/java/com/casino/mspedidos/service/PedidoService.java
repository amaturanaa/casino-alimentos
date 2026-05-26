package com.casino.mspedidos.service;

import com.casino.mspedidos.dto.PedidoRequestDTO;
import com.casino.mspedidos.dto.PedidoResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Pedido
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase PedidoServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface PedidoService {

    // Crea un nuevo pedido con sus detalles
    // Calcula el total sumando los subtotales de cada detalle
    // Estado inicial siempre es RECIBIDO
    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);

    // Obtiene un pedido por su id incluyendo sus detalles
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    PedidoResponseDTO obtenerPorId(Long id);

    // Lista pedidos filtrados por usuario
    // usuarioId es referencia a ms-usuarios sin FK física
    List<PedidoResponseDTO> listarPorUsuario(Long usuarioId);

    // Lista pedidos filtrados por sede
    // sedeId es referencia a ms-sucursales sin FK física
    List<PedidoResponseDTO> listarPorSede(Long sedeId);

    // Lista pedidos filtrados por estado
    // Ejemplo: listarPorEstado("RECIBIDO") o listarPorEstado("ENTREGADO")
    List<PedidoResponseDTO> listarPorEstado(String estado);

    // Cambia el estado de un pedido siguiendo el flujo establecido
    // Estados válidos: RECIBIDO, EN_PREPARACION, LISTO_RETIRO, ENTREGADO
    // Lanza RuntimeException si el estado no es válido
    PedidoResponseDTO cambiarEstado(Long id, String estado);

    // Lista todos los pedidos del sistema sin filtros
    List<PedidoResponseDTO> listar();
}