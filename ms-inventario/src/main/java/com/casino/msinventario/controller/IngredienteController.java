package com.casino.msinventario.controller;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.service.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crear(
            @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(ingredienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingredienteService.obtenerPorId(id));
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<IngredienteResponseDTO>> listarPorSede(@PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarPorSede(sedeId));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajo() {
        return ResponseEntity.ok(ingredienteService.listarStockBajo());
    }

    @GetMapping("/stock-bajo/sede/{sedeId}")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajoPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarStockBajoPorSede(sedeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(ingredienteService.actualizar(id, dto));
    }
}
