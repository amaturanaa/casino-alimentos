package com.casino.msusuarios.service;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;
import java.util.List;

// Interfaz del Service para la entidad Usuario
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase UsuarioServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface UsuarioService {

    // Crea un nuevo usuario validando email y RUT únicos
    // Encripta la contraseña con BCrypt antes de persistir
    // Lanza RuntimeException si email o RUT ya existen
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto);

    // Obtiene un usuario por su id
    // Lanza RuntimeException si no existe — capturada por GlobalExceptionHandler
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);

    // Obtiene un usuario por su email
    // Usado para login y búsqueda por credencial única
    UsuarioResponseDTO obtenerUsuarioPorEmail(String email);

    // Lista todos los usuarios del sistema sin filtros
    List<UsuarioResponseDTO> listarUsuarios();

    // Lista solo usuarios con estado activo = true
    // Endpoint consumido por ms-reservas via Feign para verificar usuarios
    List<UsuarioResponseDTO> listarUsuariosActivos();

    // Actualiza los datos de un usuario existente
    // Re-encripta la contraseña con BCrypt si se modifica
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto);

    // Desactiva un usuario (baja lógica — activo = false)
    // No elimina el registro de la base de datos
    void eliminarUsuario(Long id);
}