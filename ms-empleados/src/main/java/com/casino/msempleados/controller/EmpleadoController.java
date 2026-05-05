package com.casino.msempleados.controller;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(empleadoService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorRut(
            @PathVariable String rut) {
        return ResponseEntity.ok(empleadoService.obtenerPorRut(rut));
    }

    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarPorCargo(
            @PathVariable String cargo) {
        return ResponseEntity.ok(empleadoService.listarPorCargo(cargo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(empleadoService.cambiarEstado(id, activo));
    }
}