package com.casino.msmenu.controller;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.service.TipoPlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-plato")
@RequiredArgsConstructor
public class TipoPlatoController {

    private final TipoPlatoService tipoPlatoService;

    @PostMapping
    public ResponseEntity<TipoPlatoResponseDTO> crear(@Valid @RequestBody TipoPlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoPlatoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoPlatoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoPlatoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPlatoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoPlatoService.obtenerPorId(id));
    }
}
