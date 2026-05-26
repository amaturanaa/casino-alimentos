package com.casino.msusuarios.controller;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;
import com.casino.msusuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Usuario
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// ms-usuarios es consumido por ms-reservas via Feign para verificar usuarios activos
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final UsuarioService usuarioService;

    // POST /api/usuarios — Crea un nuevo usuario
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // La contraseña se encripta con BCrypt antes de persistir
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(dto));
    }

    // GET /api/usuarios — Lista todos los usuarios del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // GET /api/usuarios/activos — Lista solo usuarios con estado activo = true
    // Endpoint consumido por ms-reservas via Feign para verificar usuario activo
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarActivos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosActivos());
    }

    // GET /api/usuarios/{id} — Obtiene un usuario por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Endpoint consumido por ms-reservas via Feign para verificar usuario
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    // GET /api/usuarios/email/{email} — Obtiene un usuario por su email
    // Útil para login y búsqueda por credencial única
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorEmail(email));
    }

    // PUT /api/usuarios/{id} — Actualiza todos los datos de un usuario
    // @Valid valida el DTO antes de procesar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    // DELETE /api/usuarios/{id} — Desactiva un usuario (baja lógica)
    // ResponseEntity.noContent() retorna código HTTP 204 sin cuerpo
    // No elimina el registro físicamente — solo cambia activo a false
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}