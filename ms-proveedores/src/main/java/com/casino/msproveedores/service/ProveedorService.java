package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Proveedor
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase ProveedorServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface ProveedorService {

    // Crea un nuevo proveedor validando RUT único en el sistema
    // Lanza RuntimeException si el RUT ya existe — capturada por GlobalExceptionHandler
    ProveedorResponseDTO crear(ProveedorRequestDTO dto);

    // Obtiene un proveedor por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    ProveedorResponseDTO obtenerPorId(Long id);

    // Lista todos los proveedores del sistema sin filtros
    List<ProveedorResponseDTO> listar();

    // Lista solo proveedores con estado activo = true
    List<ProveedorResponseDTO> listarActivos();

    // Actualiza los datos de un proveedor existente
    // Solo permite modificar razonSocial, email y teléfono (no el RUT)
    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto);

    // Cambia el estado activo/inactivo de un proveedor (baja lógica)
    // No elimina el registro de la base de datos
    ProveedorResponseDTO cambiarEstado(Long id, Boolean activo);
}