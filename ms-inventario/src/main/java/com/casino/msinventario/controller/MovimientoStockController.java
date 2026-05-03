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

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoStockController {

    private final MovimientoStockService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoStockResponseDTO> registrar(
            @Valid @RequestBody MovimientoStockRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDTO>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    @GetMapping("/ingrediente/{ingredienteId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorIngrediente(
            @PathVariable Long ingredienteId) {
        return ResponseEntity.ok(movimientoService.listarPorIngrediente(ingredienteId));
    }

    @GetMapping("/tipo/{tipoMovimientoId}")
    public ResponseEntity<List<MovimientoStockResponseDTO>> listarPorTipo(
            @PathVariable Long tipoMovimientoId) {
        return ResponseEntity.ok(movimientoService.listarPorTipo(tipoMovimientoId));
    }
}
