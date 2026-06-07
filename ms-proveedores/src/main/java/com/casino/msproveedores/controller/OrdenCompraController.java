package com.casino.msproveedores.controller;

import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.service.OrdenCompraService;
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

// Controlador REST que expone los endpoints del recurso OrdenCompra
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Al crear una orden se verifica la sede via Feign con ms-sucursales
@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Compra", description = "Gestión de órdenes de compra a proveedores")
public class OrdenCompraController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final OrdenCompraService ordenCompraService;

    // POST /api/ordenes — Crea una nueva orden de compra
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear una orden de compra",
            description = "Registra una nueva orden de compra. Verifica la sede via Feign con ms-sucursales")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Orden creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o sede no operativa")
    })
    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> crear(
            @Valid @RequestBody OrdenCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenCompraService.crear(dto));
    }

    // GET /api/ordenes — Lista todas las órdenes de compra del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(summary = "Listar todas las órdenes",
            description = "Obtiene la lista completa de órdenes de compra registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> listar() {
        return ResponseEntity.ok(ordenCompraService.listar());
    }

    // GET /api/ordenes/{id} — Obtiene una orden de compra por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(summary = "Obtener una orden por id",
            description = "Busca y retorna una orden de compra según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden encontrada"),
            @ApiResponse(responseCode = "400", description = "Orden no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único de la orden de compra")
            @PathVariable Long id) {
        return ResponseEntity.ok(ordenCompraService.obtenerPorId(id));
    }

    // GET /api/ordenes/proveedor/{proveedorId} — Lista órdenes de un proveedor específico
    @Operation(summary = "Listar órdenes por proveedor",
            description = "Obtiene todas las órdenes de compra de un proveedor específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de órdenes del proveedor obtenida correctamente")
    })
    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorProveedor(
            @Parameter(description = "Identificador del proveedor")
            @PathVariable Long proveedorId) {
        return ResponseEntity.ok(ordenCompraService.listarPorProveedor(proveedorId));
    }

    // GET /api/ordenes/sede/{sedeId} — Lista órdenes de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(summary = "Listar órdenes por sede",
            description = "Obtiene todas las órdenes de compra asociadas a una sede específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de órdenes de la sede obtenida correctamente")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorSede(
            @Parameter(description = "Identificador de la sede (referencia a ms-sucursales)")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ordenCompraService.listarPorSede(sedeId));
    }

    // GET /api/ordenes/estado/{estado} — Lista órdenes filtradas por estado
    // Ejemplo: PENDIENTE, RECIBIDA, CANCELADA
    @Operation(summary = "Listar órdenes por estado",
            description = "Filtra las órdenes según su estado: PENDIENTE, RECIBIDA, CANCELADA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de órdenes filtrada correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado a filtrar: PENDIENTE, RECIBIDA o CANCELADA")
            @PathVariable String estado) {
        return ResponseEntity.ok(ordenCompraService.listarPorEstado(estado));
    }

    // PATCH /api/ordenes/{id}/estado — Cambia el estado de una orden
    // @RequestParam recibe el parámetro desde la URL: ?estado=RECIBIDA
    // Usa PATCH porque modifica parcialmente el recurso
    @Operation(summary = "Cambiar el estado de una orden",
            description = "Actualiza el estado de una orden de compra según el valor enviado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de la orden actualizado"),
            @ApiResponse(responseCode = "400", description = "Orden no encontrada o estado inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenCompraResponseDTO> cambiarEstado(
            @Parameter(description = "Identificador único de la orden de compra")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la orden")
            @RequestParam String estado) {
        return ResponseEntity.ok(ordenCompraService.cambiarEstado(id, estado));
    }
}