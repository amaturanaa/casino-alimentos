package com.casino.msusuarios.service;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;
import com.casino.msusuarios.model.Rol;
import com.casino.msusuarios.model.Usuario;
import com.casino.msusuarios.repository.RolRepository;
import com.casino.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Usuario
// Contiene toda la lógica de negocio relacionada a usuarios del sistema
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    // Repositorio JPA para acceso a datos de Usuario
    private final UsuarioRepository usuarioRepository;

    // Repositorio JPA para acceso a datos de Rol
    // Necesario para verificar que el rol exista antes de asignarlo al usuario
    private final RolRepository rolRepository;

    // Bean de Spring Security para encriptar contraseñas con BCrypt
    // Inyectado desde SecurityConfig
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        log.info("Creando usuario con email: {}", dto.getEmail());

        // Validación de negocio: email único en el sistema
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email ya registrado: {}", dto.getEmail());
            throw new RuntimeException("El email ya está registrado");
        }

        // Validación de negocio: RUT único en el sistema
        if (usuarioRepository.existsByRutUsuario(dto.getRut_usuario())) {
            log.warn("RUT ya registrado: {}", dto.getRut_usuario());
            throw new RuntimeException("El RUT ya está registrado");
        }

        // Verifica que el rol exista antes de asignarlo
        Rol rol = rolRepository.findById(dto.getRol_id())
                .orElseThrow(() -> {
                    log.error("Rol no encontrado: {}", dto.getRol_id());
                    return new RuntimeException("Rol no encontrado");
                });

        // Construcción de la entidad Usuario desde el DTO de entrada
        // La contraseña se encripta con BCrypt antes de persistir
        // NUNCA se guarda la contraseña en texto plano
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

        // Persistencia en base de datos mediante JpaRepository
        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado con id: {}", guardado.getIdUsuario());

        // Retorna DTO en vez de entidad — la contraseña no se incluye en la respuesta
        return mapToDTO(guardado);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        log.info("Buscando usuario con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con id: {}", id);
                    return new RuntimeException("Usuario no encontrado");
                });
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {
        log.info("Buscando usuario con email: {}", email);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email: {}", email);
                    return new RuntimeException("Usuario no encontrado");
                });
        return mapToDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        log.info("Listando todos los usuarios");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<UsuarioResponseDTO> lista = new ArrayList<>();
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario u : usuarios) {
            lista.add(mapToDTO(u));
        }

        log.info("Total usuarios encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuariosActivos() {
        log.info("Listando usuarios activos");

        List<UsuarioResponseDTO> lista = new ArrayList<>();
        List<Usuario> usuarios = usuarioRepository.findByActivo(true);

        for (Usuario u : usuarios) {
            lista.add(mapToDTO(u));
        }

        log.info("Total usuarios activos: {}", lista.size());
        return lista;
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        log.info("Actualizando usuario con id: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado para actualizar: {}", id);
                    return new RuntimeException("Usuario no encontrado");
                });

        // Verifica que el nuevo rol exista antes de asignarlo
        Rol rol = rolRepository.findById(dto.getRol_id())
                .orElseThrow(() -> {
                    log.error("Rol no encontrado: {}", dto.getRol_id());
                    return new RuntimeException("Rol no encontrado");
                });

        // Actualiza los campos del usuario
        // La contraseña se re-encripta con BCrypt si se modifica
        usuario.setPnombreUsuario(dto.getPnombre_usuario());
        usuario.setSnombreUsuario(dto.getSnombre_usuario());
        usuario.setAppaternoUsuario(dto.getAppaterno_usuario());
        usuario.setApmaternoUsuario(dto.getApmaterno_usuario());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(rol);

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado con id: {}", actualizado.getIdUsuario());
        return mapToDTO(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        log.info("Desactivando usuario con id: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado para desactivar: {}", id);
                    return new RuntimeException("Usuario no encontrado");
                });

        // Baja lógica — no elimina el registro, solo cambia activo a false
        // Preserva el historial del usuario en la base de datos
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        log.info("Usuario {} desactivado exitosamente", id);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // La contraseña NO se incluye en el DTO por seguridad
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