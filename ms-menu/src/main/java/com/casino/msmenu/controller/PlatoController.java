package com.casino.msmenu.controller;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crear(@Valid @RequestBody PlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> listar() {
        return ResponseEntity.ok(platoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(platoService.obtenerPorId(id));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<PlatoResponseDTO> cambiarDisponibilidad(
            @PathVariable Long id, @RequestParam Boolean disponible) {
        return ResponseEntity.ok(platoService.cambiarDisponibilidad(id, disponible));
    }

    @GetMapping("/tipo/{tipoPlatoId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorTipo(@PathVariable Long tipoPlatoId) {
        return ResponseEntity.ok(platoService.listarPorTipo(tipoPlatoId));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(platoService.listarPorCategoria(categoriaId));
    }
}
