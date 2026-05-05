package com.casino.msempleados.controller;

import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import com.casino.msempleados.service.TurnoEmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos-empleado")
@RequiredArgsConstructor
public class TurnoEmpleadoController {

    private final TurnoEmpleadoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoEmpleadoResponseDTO> crear(
            @Valid @RequestBody TurnoEmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoEmpleadoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorEmpleado(
            @PathVariable Long idEmpleado) {
        return ResponseEntity.ok(turnoService.listarPorEmpleado(idEmpleado));
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    @GetMapping("/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSedeYFecha(
            @PathVariable Long sedeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorSedeYFecha(sedeId, fecha));
    }

    @GetMapping("/tipo/{tipoTurno}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorTipo(
            @PathVariable String tipoTurno) {
        return ResponseEntity.ok(turnoService.listarPorTipo(tipoTurno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
