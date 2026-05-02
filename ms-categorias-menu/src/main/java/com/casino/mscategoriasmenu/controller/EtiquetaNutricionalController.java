package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.service.EtiquetaNutricionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etiquetas")
@RequiredArgsConstructor
public class EtiquetaNutricionalController {

    private final EtiquetaNutricionalService etiquetaService;

    @PostMapping
    public ResponseEntity<EtiquetaNutricionalResponseDTO> crear(
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<EtiquetaNutricionalResponseDTO>> listar() {
        return ResponseEntity.ok(etiquetaService.listar());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> obtenerPorCategoria(
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(etiquetaService.obtenerPorCategoria(categoriaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.ok(etiquetaService.actualizar(id, dto));
    }
}
