package com.casino.msproveedores.controller;

import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.service.OrdenCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso OrdenCompra
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Al crear una orden se verifica la sede via Feign con ms-sucursales
@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenCompraController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final OrdenCompraService ordenCompraService;

    // POST /api/ordenes — Crea una nueva orden de compra
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> crear(
            @Valid @RequestBody OrdenCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenCompraService.crear(dto));
    }

    // GET /api/ordenes — Lista todas las órdenes de compra del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> listar() {
        return ResponseEntity.ok(ordenCompraService.listar());
    }

    // GET /api/ordenes/{id} — Obtiene una orden de compra por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenCompraService.obtenerPorId(id));
    }

    // GET /api/ordenes/proveedor/{proveedorId} — Lista órdenes de un proveedor específico
    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorProveedor(
            @PathVariable Long proveedorId) {
        return ResponseEntity.ok(ordenCompraService.listarPorProveedor(proveedorId));
    }

    // GET /api/ordenes/sede/{sedeId} — Lista órdenes de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ordenCompraService.listarPorSede(sedeId));
    }

    // GET /api/ordenes/estado/{estado} — Lista órdenes filtradas por estado
    // Ejemplo: PENDIENTE, RECIBIDA, CANCELADA
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(ordenCompraService.listarPorEstado(estado));
    }

    // PATCH /api/ordenes/{id}/estado — Cambia el estado de una orden
    // @RequestParam recibe el parámetro desde la URL: ?estado=RECIBIDA
    // Usa PATCH porque modifica parcialmente el recurso
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenCompraResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(ordenCompraService.cambiarEstado(id, estado));
    }
}