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

@RestController
@RequestMapping("/api/tipos-movimiento")
@RequiredArgsConstructor
public class TipoMovimientoController {

    private final TipoMovimientoService tipoMovimientoService;

    @PostMapping
    public ResponseEntity<TipoMovimientoResponseDTO> crear(
            @Valid @RequestBody TipoMovimientoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMovimientoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoMovimientoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoMovimientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoMovimientoService.obtenerPorId(id));
    }
}
