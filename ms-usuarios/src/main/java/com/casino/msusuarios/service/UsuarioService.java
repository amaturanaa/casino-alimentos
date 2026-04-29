package com.casino.msusuarios.service;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto);
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);
    UsuarioResponseDTO obtenerUsuarioPorEmail(String email);
    List<UsuarioResponseDTO> listarUsuarios();
    List<UsuarioResponseDTO> listarUsuariosActivos();
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto);
    void eliminarUsuario(Long id);
}
