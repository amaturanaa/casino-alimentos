package com.casino.msproveedores.controller;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.service.ProveedorService;
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

// Controlador REST que expone los endpoints del recurso Proveedor
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "Gestión de los proveedores del casino de alimentos")
public class ProveedorController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final ProveedorService proveedorService;

    // POST /api/proveedores — Crea un nuevo proveedor
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear un proveedor",
            description = "Registra un nuevo proveedor en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proveedorService.crear(dto));
    }

    // GET /api/proveedores — Lista todos los proveedores del sistema
    @Operation(summary = "Listar todos los proveedores",
            description = "Obtiene la lista completa de proveedores registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar() {
        return ResponseEntity.ok(proveedorService.listar());
    }

    // GET /api/proveedores/activos — Lista solo proveedores con estado activo = true
    @Operation(summary = "Listar proveedores activos",
            description = "Obtiene solo los proveedores con estado activo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de proveedores activos obtenida correctamente")
    })
    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorResponseDTO>> listarActivos() {
        return ResponseEntity.ok(proveedorService.listarActivos());
    }

    // GET /api/proveedores/{id} — Obtiene un proveedor por su id
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(summary = "Obtener un proveedor por id",
            description = "Busca y retorna un proveedor según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
            @ApiResponse(responseCode = "400", description = "Proveedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único del proveedor")
            @PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    // PUT /api/proveedores/{id} — Actualiza todos los datos de un proveedor
    // @Valid valida el DTO antes de procesar
    @Operation(summary = "Actualizar un proveedor",
            description = "Actualiza todos los datos de un proveedor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Proveedor no encontrado o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @Parameter(description = "Identificador único del proveedor")
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizar(id, dto));
    }

    // PATCH /api/proveedores/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?activo=true o ?activo=false
    @Operation(summary = "Cambiar el estado de un proveedor",
            description = "Activa o desactiva un proveedor según el estado enviado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del proveedor actualizado"),
            @ApiResponse(responseCode = "400", description = "Proveedor no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProveedorResponseDTO> cambiarEstado(
            @Parameter(description = "Identificador único del proveedor")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado: true (activo) o false (inactivo)")
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(proveedorService.cambiarEstado(id, activo));
    }
}