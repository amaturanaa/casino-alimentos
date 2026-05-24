package com.casino.msmenu.controller;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.service.ProgramacionDiariaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

// Controlador REST que expone los endpoints del recurso ProgramacionDiaria
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// La programación diaria define qué platos se sirven en cada sede por fecha
@RestController
@RequestMapping("/api/programacion")
@RequiredArgsConstructor
public class ProgramacionDiariaController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final ProgramacionDiariaService programacionService;

    // POST /api/programacion — Crea una nueva programación diaria
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<ProgramacionDiariaResponseDTO> crear(
            @Valid @RequestBody ProgramacionDiariaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programacionService.crear(dto));
    }

    // GET /api/programacion/fecha/{fecha} — Lista programaciones por fecha
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    // Ejemplo: GET /api/programacion/fecha/2026-05-15
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(programacionService.listarPorFecha(fecha));
    }

    // GET /api/programacion/sede/{sedeId} — Lista programaciones por sede
    // @PathVariable extrae el id de la sede desde la URL
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorSede(sedeId));
    }

    // GET /api/programacion/fecha/{fecha}/sede/{sedeId}
    // Lista programaciones filtradas por fecha y sede simultáneamente
    // Útil para ver el menú del día de una sede específica
    @GetMapping("/fecha/{fecha}/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFechaYSede(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorFechaYSede(fecha, sedeId));
    }

    // PATCH /api/programacion/{id}/descontar — Descuenta una ración de la programación
    // Se llama cuando un cliente realiza un pedido de ese plato en esa fecha
    // @NotNull valida que el id no sea nulo
    // Retorna la programación actualizada con el nuevo conteo de raciones
    @PatchMapping("/{id}/descontar")
    public ResponseEntity<ProgramacionDiariaResponseDTO> descontarRacion(
            @PathVariable @NotNull(message = "El id de la programación es obligatorio")
            Long id) {
        return ResponseEntity.ok(programacionService.descontarRacion(id));
    }
}