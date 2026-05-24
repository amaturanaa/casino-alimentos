package com.casino.msinventario.controller;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import com.casino.msinventario.service.TipoMovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso TipoMovimiento
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Los tipos de movimiento clasifican los movimientos de stock
// Ejemplo: ENTRADA (recepción), SALIDA (consumo), MERMA (pérdida)
@RestController
@RequestMapping("/api/tipos-movimiento")
@RequiredArgsConstructor
public class TipoMovimientoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TipoMovimientoService tipoMovimientoService;

    // POST /api/tipos-movimiento — Crea un nuevo tipo de movimiento
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<TipoMovimientoResponseDTO> crear(
            @Valid @RequestBody TipoMovimientoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoMovimientoService.crear(dto));
    }

    // GET /api/tipos-movimiento — Lista todos los tipos de movimiento del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<TipoMovimientoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoMovimientoService.listar());
    }

    // GET /api/tipos-movimiento/{id} — Obtiene un tipo de movimiento por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(tipoMovimientoService.obtenerPorId(id));
    }
}