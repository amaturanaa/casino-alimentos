package com.casino.msinventario.controller;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import com.casino.msinventario.service.TipoMovimientoService;
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

// Controlador REST que expone los endpoints del recurso TipoMovimiento
// Los tipos de movimiento clasifican los movimientos de stock
// Ejemplo: ENTRADA (recepción), SALIDA (consumo), MERMA (pérdida)
// @Tag agrupa todos los endpoints de este controller bajo "Tipos de Movimiento"
@RestController
@RequestMapping("/api/tipos-movimiento")
@RequiredArgsConstructor
@Tag(name = "Tipos de Movimiento",
        description = "Gestión de tipos de movimiento de stock (ENTRADA, SALIDA, MERMA)")
public class TipoMovimientoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TipoMovimientoService tipoMovimientoService;

    // POST /api/tipos-movimiento — Crea un nuevo tipo de movimiento
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear tipo de movimiento",
            description = "Registra un nuevo tipo de movimiento validando nombre único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de movimiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre ya existe")
    })
    @PostMapping
    public ResponseEntity<TipoMovimientoResponseDTO> crear(
            @Valid @RequestBody TipoMovimientoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoMovimientoService.crear(dto));
    }

    // GET /api/tipos-movimiento — Lista todos los tipos de movimiento del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar tipos de movimiento",
            description = "Retorna la lista completa de tipos de movimiento registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos registrada")
    })
    @GetMapping
    public ResponseEntity<List<TipoMovimientoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoMovimientoService.listar());
    }

    // GET /api/tipos-movimiento/{id} — Obtiene un tipo de movimiento por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener tipo de movimiento por ID",
            description = "Retorna un tipo de movimiento según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de movimiento encontrado"),
            @ApiResponse(responseCode = "400", description = "Tipo de movimiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del tipo de movimiento a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(tipoMovimientoService.obtenerPorId(id));
    }
}