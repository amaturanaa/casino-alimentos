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

// Controlador REST que expone los endpoints del recurso TurnoDisponible
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Los turnos disponibles definen los horarios de comedor por sede y fecha
@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoDisponibleController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TurnoDisponibleService turnoService;

    // POST /api/turnos — Crea un nuevo turno disponible
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<TurnoDisponibleResponseDTO> crear(
            @Valid @RequestBody TurnoDisponibleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crear(dto));
    }

    // GET /api/turnos — Lista todos los turnos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    // GET /api/turnos/{id} — Obtiene un turno por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDisponibleResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // GET /api/turnos/sede/{sedeId} — Lista turnos de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    // GET /api/turnos/fecha/{fecha} — Lista turnos de una fecha específica
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    // Ejemplo: GET /api/turnos/fecha/2026-05-15
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    // GET /api/turnos/disponibles/sede/{sedeId}/fecha/{fecha}
    // Lista turnos con cupos disponibles filtrados por sede y fecha
    // Endpoint principal para que los usuarios vean qué turnos pueden reservar
    @GetMapping("/disponibles/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarDisponibles(
            @PathVariable Long sedeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarDisponiblesPorSedeYFecha(sedeId, fecha));
    }
}