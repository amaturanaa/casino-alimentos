package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad OrdenCompra
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase OrdenCompraServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface OrdenCompraService {

    // Crea una nueva orden de compra verificando sede operativa via Feign con ms-sucursales
    // Calcula el costo total sumando cantidad * precioUnitario de cada detalle
    // Lanza RuntimeException si el proveedor no existe o la sede no está operativa
    OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto);

    // Obtiene una orden de compra por su id incluyendo sus detalles
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    OrdenCompraResponseDTO obtenerPorId(Long id);

    // Lista todas las órdenes de compra del sistema sin filtros
    List<OrdenCompraResponseDTO> listar();

    // Lista órdenes filtradas por proveedor específico
    List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId);

    // Lista órdenes filtradas por sede específica
    // sedeId es referencia a ms-sucursales sin FK física
    List<OrdenCompraResponseDTO> listarPorSede(Long sedeId);

    // Lista órdenes filtradas por estado
    // Valores válidos: PENDIENTE, RECIBIDA, CANCELADA
    List<OrdenCompraResponseDTO> listarPorEstado(String estado);

    // Cambia el estado de una orden de compra existente
    // Lanza RuntimeException si el estado no es válido
    OrdenCompraResponseDTO cambiarEstado(Long id, String estado);
}