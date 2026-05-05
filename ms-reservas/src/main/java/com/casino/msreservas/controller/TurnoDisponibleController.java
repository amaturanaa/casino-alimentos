package com.casino.msreservas.controller;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.service.TurnoDisponibleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoDisponibleController {

    private final TurnoDisponibleService turnoService;

    @PostMapping
    public ResponseEntity<TurnoDisponibleResponseDTO> crear(
            @Valid @RequestBody TurnoDisponibleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDisponibleResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    @GetMapping("/disponibles/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarDisponibles(
            @PathVariable Long sedeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarDisponiblesPorSedeYFecha(sedeId, fecha));
    }
}