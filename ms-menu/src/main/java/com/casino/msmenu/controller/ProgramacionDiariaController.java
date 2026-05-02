package com.casino.msmenu.controller;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.service.ProgramacionDiariaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/programacion")
@RequiredArgsConstructor
public class ProgramacionDiariaController {

    private final ProgramacionDiariaService programacionService;

    @PostMapping
    public ResponseEntity<ProgramacionDiariaResponseDTO> crear(
            @Valid @RequestBody ProgramacionDiariaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programacionService.crear(dto));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(programacionService.listarPorFecha(fecha));
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorSede(sedeId));
    }

    @GetMapping("/fecha/{fecha}/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFechaYSede(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorFechaYSede(fecha, sedeId));
    }

    @PatchMapping("/{id}/descontar")
    public ResponseEntity<ProgramacionDiariaResponseDTO> descontarRacion(@PathVariable Long id) {
        return ResponseEntity.ok(programacionService.descontarRacion(id));
    }
}
