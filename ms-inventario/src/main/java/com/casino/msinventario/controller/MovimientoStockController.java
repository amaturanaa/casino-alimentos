package com.casino.msinventario.controller;

import com.casino.msinventario.dto.MovimientoStockRequestDTO;
import com.casino.msinventario.dto.MovimientoStockResponseDTO;
import com.casino.msinventario.service.MovimientoStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso MovimientoStock
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Registra entradas, salidas y mermas de ingredientes en el inventario
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoStockController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final MovimientoStockService movimientoService;

    // POST /api/movimientos — Registra un nuevo movimiento de stock
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Actualiza automáticamente el stock del ingrediente afectado
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<MovimientoStockResponseDTO> registrar(
            @Valid @RequestBody MovimientoStockRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimientoService.registrar(dto));
    }

    // GET /api/movimientos — Lista todos los movimientos de stock del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDTO>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    // GET /api/movimientos/ingrediente/{ingredienteId}
    // Lista todos los movimientos de un ingrediente específico
    // @PathVariable extrae el id del ingrediente desde la URL
    // Útil para ver el historial de movimientos de un ingrediente
    @GetMapping("/ingrediente/{ingredienteId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorIngrediente(
            @PathVariable Long ingredienteId) {
        return ResponseEntity.ok(movimientoService.listarPorIngrediente(ingredienteId));
    }

    // GET /api/movimientos/tipo/{tipoMovimientoId}
    // Lista movimientos filtrados por tipo de movimiento
    // @PathVariable extrae el id del tipo de movimiento desde la URL
    // Ejemplo: listar solo ENTRADAS, solo SALIDAS o solo MERMAS
    @GetMapping("/tipo/{tipoMovimientoId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorTipo(
            @PathVariable Long tipoMovimientoId) {
        return ResponseEntity.ok(movimientoService.listarPorTipo(tipoMovimientoId));
    }
}