package com.casino.msreservas.controller;

import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import com.casino.msreservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Reserva
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Al crear una reserva se verifica usuario via Feign con ms-usuarios
// y sede via Feign con ms-sucursales
@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final ReservaService reservaService;

    // POST /api/reservas — Crea una nueva reserva
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica usuario activo via Feign con ms-usuarios
    // Verifica sede operativa via Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.crear(dto));
    }

    // GET /api/reservas — Lista todas las reservas del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    // GET /api/reservas/{id} — Obtiene una reserva por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    // GET /api/reservas/usuario/{usuarioId} — Lista reservas de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física entre bases de datos
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarPorUsuario(usuarioId));
    }

    // GET /api/reservas/turno/{turnoId} — Lista reservas de un turno específico
    // Útil para ver cuántos comensales tienen reserva en un turno
    @GetMapping("/turno/{turnoId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorTurno(
            @PathVariable Long turnoId) {
        return ResponseEntity.ok(reservaService.listarPorTurno(turnoId));
    }

    // GET /api/reservas/sede/{sedeId} — Lista reservas de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(reservaService.listarPorSede(sedeId));
    }

    // GET /api/reservas/estado/{estado} — Lista reservas filtradas por estado
    // Ejemplo: GET /api/reservas/estado/ACTIVA o GET /api/reservas/estado/CANCELADA
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(reservaService.listarPorEstado(estado));
    }

    // PATCH /api/reservas/{id}/cancelar — Cancela una reserva existente
    // Usa PATCH porque modifica parcialmente el recurso (solo el estado)
    // Devuelve automáticamente el cupo al turno al cancelar
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}