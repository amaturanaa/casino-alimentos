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

// Controlador REST que expone los endpoints del recurso TurnoEmpleado
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
@RestController
@RequestMapping("/api/turnos-empleado")
@RequiredArgsConstructor
public class TurnoEmpleadoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TurnoEmpleadoService turnoService;

    // POST /api/turnos-empleado — Crea un nuevo turno para un empleado
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<TurnoEmpleadoResponseDTO> crear(
            @Valid @RequestBody TurnoEmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crear(dto));
    }

    // GET /api/turnos-empleado — Lista todos los turnos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    // GET /api/turnos-empleado/{id} — Obtiene un turno por su id
    // @PathVariable extrae el valor {id} desde la URL
    @GetMapping("/{id}")
    public ResponseEntity<TurnoEmpleadoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // GET /api/turnos-empleado/empleado/{idEmpleado} — Lista turnos de un empleado
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorEmpleado(
            @PathVariable Long idEmpleado) {
        return ResponseEntity.ok(turnoService.listarPorEmpleado(idEmpleado));
    }

    // GET /api/turnos-empleado/sede/{sedeId} — Lista turnos de una sede
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    // GET /api/turnos-empleado/fecha/{fecha} — Lista turnos de una fecha específica
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    // GET /api/turnos-empleado/sede/{sedeId}/fecha/{fecha}
    // Lista turnos filtrados por sede y fecha simultáneamente
    @GetMapping("/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSedeYFecha(
            @PathVariable Long sedeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorSedeYFecha(sedeId, fecha));
    }

    // GET /api/turnos-empleado/tipo/{tipoTurno} — Lista turnos por tipo
    // Ejemplo: MAÑANA, TARDE, NOCHE
    @GetMapping("/tipo/{tipoTurno}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorTipo(
            @PathVariable String tipoTurno) {
        return ResponseEntity.ok(turnoService.listarPorTipo(tipoTurno));
    }

    // DELETE /api/turnos-empleado/{id} — Elimina un turno por su id
    // ResponseEntity.noContent() retorna código HTTP 204 sin cuerpo de respuesta
    // 204 indica éxito en la operación pero sin contenido que retornar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}