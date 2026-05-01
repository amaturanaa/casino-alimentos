package com.casino.mssucursales.controller;


import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.service.SedeCasinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
public class SedeCasinoController {

    private final SedeCasinoService sedeCasinoService;

    @PostMapping
    public ResponseEntity<SedeCasinoResponseDTO> crear(
            @RequestBody SedeCasinoRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sedeCasinoService.crearSede(request));
    }

    @GetMapping
    public ResponseEntity<List<SedeCasinoResponseDTO>> listar() {
        return ResponseEntity.ok(sedeCasinoService.listarSedes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedeCasinoResponseDTO> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(sedeCasinoService.obtenerPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<SedeCasinoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean estado) {

        return ResponseEntity.ok(
                sedeCasinoService.cambiarEstado(id, estado)
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SedeCasinoResponseDTO>> listarPorEstado(
            @PathVariable Boolean estado) {

        return ResponseEntity.ok(
                sedeCasinoService.listarPorEstado(estado)
        );
    }

}
