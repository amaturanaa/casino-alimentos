package com.casino.msusuarios.controller;

import com.casino.msusuarios.dto.UsuarioRequestDTO;
import com.casino.msusuarios.dto.UsuarioResponseDTO;
import com.casino.msusuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Usuario
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// ms-usuarios es consumido por ms-reservas via Feign para verificar usuarios activos
// @Tag agrupa todos los endpoints de este controller bajo "Usuarios"
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del casino")
public class UsuarioController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final UsuarioService usuarioService;

    // POST /api/usuarios — Crea un nuevo usuario
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // La contraseña se encripta con BCrypt antes de persistir
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario. La contraseña se encripta con BCrypt antes de guardar"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(dto));
    }

    // GET /api/usuarios — Lista todos los usuarios del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar usuarios",
            description = "Retorna la lista completa de usuarios registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // GET /api/usuarios/activos — Lista solo usuarios con estado activo = true
    // Endpoint consumido por ms-reservas via Feign para verificar usuario activo
    @Operation(
            summary = "Listar usuarios activos",
            description = "Retorna solo los usuarios activos. Consumido por ms-reservas via Feign"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios activos obtenida")
    })
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarActivos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosActivos());
    }

    // GET /api/usuarios/{id} — Obtiene un usuario por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Endpoint consumido por ms-reservas via Feign para verificar usuario
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Retorna un usuario según su ID. Consumido por ms-reservas via Feign"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(
            @Parameter(description = "ID del usuario a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    // GET /api/usuarios/email/{email} — Obtiene un usuario por su email
    // Útil para login y búsqueda por credencial única
    @Operation(
            summary = "Obtener usuario por email",
            description = "Retorna un usuario según su email. Útil para login y búsqueda por credencial única"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorEmail(
            @Parameter(description = "Email del usuario a buscar")
            @PathVariable String email) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorEmail(email));
    }

    // PUT /api/usuarios/{id} — Actualiza todos los datos de un usuario
    // @Valid valida el DTO antes de procesar
    @Operation(
            summary = "Actualizar usuario",
            description = "Reemplaza todos los datos de un usuario existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @Parameter(description = "ID del usuario a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    // DELETE /api/usuarios/{id} — Desactiva un usuario (baja lógica)
    // ResponseEntity.noContent() retorna código HTTP 204 sin cuerpo
    // No elimina el registro físicamente — solo cambia activo a false
    @Operation(
            summary = "Eliminar usuario",
            description = "Desactiva un usuario (baja lógica). No elimina el registro físicamente, solo cambia su estado a inactivo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario desactivado exitosamente, sin contenido"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a desactivar")
            @PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}