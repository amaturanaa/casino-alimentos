package com.casino.msproveedores.controller;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.service.ProveedorService;
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
public class ProveedorController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final ProveedorService proveedorService;

    // POST /api/proveedores — Crea un nuevo proveedor
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proveedorService.crear(dto));
    }

    // GET /api/proveedores — Lista todos los proveedores del sistema
    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar() {
        return ResponseEntity.ok(proveedorService.listar());
    }

    // GET /api/proveedores/activos — Lista solo proveedores con estado activo = true
    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorResponseDTO>> listarActivos() {
        return ResponseEntity.ok(proveedorService.listarActivos());
    }

    // GET /api/proveedores/{id} — Obtiene un proveedor por su id
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    // PUT /api/proveedores/{id} — Actualiza todos los datos de un proveedor
    // @Valid valida el DTO antes de procesar
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizar(id, dto));
    }

    // PATCH /api/proveedores/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?activo=true o ?activo=false
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProveedorResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(proveedorService.cambiarEstado(id, activo));
    }
}