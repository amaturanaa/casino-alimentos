package com.casino.msusuarios.service;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;
import com.casino.msusuarios.model.Rol;
import com.casino.msusuarios.model.Usuario;
import com.casino.msusuarios.repository.RolRepository;
import com.casino.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("El email ya está registrado");
        if (usuarioRepository.existsByRutUsuario(dto.getRut_usuario()))
            throw new RuntimeException("El RUT ya está registrado");

        Rol rol = rolRepository.findById(dto.getRol_id())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setRutUsuario(dto.getRut_usuario());
        usuario.setPnombreUsuario(dto.getPnombre_usuario());
        usuario.setSnombreUsuario(dto.getSnombre_usuario());
        usuario.setAppaternoUsuario(dto.getAppaterno_usuario());
        usuario.setApmaternoUsuario(dto.getApmaterno_usuario());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setActivo(true);
        usuario.setRol(rol);

        return mapToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuariosActivos() {
        return usuarioRepository.findByActivo(true)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(dto.getRol_id())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setPnombreUsuario(dto.getPnombre_usuario());
        usuario.setSnombreUsuario(dto.getSnombre_usuario());
        usuario.setAppaternoUsuario(dto.getAppaterno_usuario());
        usuario.setApmaternoUsuario(dto.getApmaterno_usuario());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(rol);

        return mapToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId_usuario(u.getIdUsuario());
        dto.setRut_usuario(u.getRutUsuario());
        dto.setPnombre_usuario(u.getPnombreUsuario());
        dto.setSnombre_usuario(u.getSnombreUsuario());
        dto.setAppaterno_usuario(u.getAppaternoUsuario());
        dto.setApmaterno_usuario(u.getApmaternoUsuario());
        dto.setEmail(u.getEmail());
        dto.setActivo(u.getActivo());
        dto.setNombre_rol(u.getRol().getNombreRol());
        return dto;
    }

}
