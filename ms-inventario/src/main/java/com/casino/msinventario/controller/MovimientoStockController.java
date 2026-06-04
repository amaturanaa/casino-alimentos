package com.casino.msinventario.controller;

import com.casino.msinventario.dto.MovimientoStockRequestDTO;
import com.casino.msinventario.dto.MovimientoStockResponseDTO;
import com.casino.msinventario.service.MovimientoStockService;
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

// Controlador REST que expone los endpoints del recurso MovimientoStock
// Registra entradas, salidas y mermas de ingredientes en el inventario
// @Tag agrupa todos los endpoints de este controller bajo "Movimientos de Stock"
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos de Stock",
        description = "Registro de entradas, salidas y mermas de ingredientes")
public class MovimientoStockController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final MovimientoStockService movimientoService;

    // POST /api/movimientos — Registra un nuevo movimiento de stock
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Actualiza automáticamente el stock del ingrediente afectado
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Registrar movimiento de stock",
            description = "Registra un movimiento (ENTRADA, SALIDA o MERMA) y actualiza el stock del ingrediente automáticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, ingrediente no encontrado o stock insuficiente")
    })
    @PostMapping
    public ResponseEntity<MovimientoStockResponseDTO> registrar(
            @Valid @RequestBody MovimientoStockRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimientoService.registrar(dto));
    }

    // GET /api/movimientos — Lista todos los movimientos de stock del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar movimientos de stock",
            description = "Retorna la lista completa de movimientos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista completa de los movimientos")
    })
    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDTO>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    // GET /api/movimientos/ingrediente/{ingredienteId}
    // Lista todos los movimientos de un ingrediente específico
    // @PathVariable extrae el id del ingrediente desde la URL
    // Útil para ver el historial de movimientos de un ingrediente
    @Operation(
            summary = "Listar movimientos por ingrediente",
            description = "Retorna el historial completo de movimientos de un ingrediente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de movimientos obtenido según ingrediente")
    })
    @GetMapping("/ingrediente/{ingredienteId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorIngrediente(
            @Parameter(description = "ID del ingrediente")
            @PathVariable Long ingredienteId) {
        return ResponseEntity.ok(movimientoService.listarPorIngrediente(ingredienteId));
    }

    // GET /api/movimientos/tipo/{tipoMovimientoId}
    // Lista movimientos filtrados por tipo de movimiento
    // @PathVariable extrae el id del tipo de movimiento desde la URL
    // Ejemplo: listar solo ENTRADAS, solo SALIDAS o solo MERMAS
    @Operation(
            summary = "Listar movimientos por tipo",
            description = "Retorna los movimientos de un tipo específico (ENTRADA, SALIDA o MERMA)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos según su tipo de acción")
    })
    @GetMapping("/tipo/{tipoMovimientoId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorTipo(
            @Parameter(description = "ID del tipo de movimiento")
            @PathVariable Long tipoMovimientoId) {
        return ResponseEntity.ok(movimientoService.listarPorTipo(tipoMovimientoId));
    }
}