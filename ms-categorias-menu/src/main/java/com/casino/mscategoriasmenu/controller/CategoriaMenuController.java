package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.service.CategoriaMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaMenuController {

    private final CategoriaMenuService service;

    @PostMapping
    public ResponseEntity<CategoriaMenuResponseDTO> crear(
            @RequestBody CategoriaMenuRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMenuResponseDTO> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CategoriaMenuResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean estado) {

        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listarPorEstado(
            @PathVariable Boolean estado) {

        return ResponseEntity.ok(service.listarPorEstado(estado));
    }
}
