package com.casino.msreservas.controller;

import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import com.casino.msreservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reservas", description = "Gestión de reservas de comensales del casino de alimentos")
public class ReservaController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final ReservaService reservaService;

    // POST /api/reservas — Crea una nueva reserva
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica usuario activo via Feign con ms-usuarios
    // Verifica sede operativa via Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear una reserva",
            description = "Registra una nueva reserva. Verifica el usuario via ms-usuarios y la sede via ms-sucursales")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, usuario inactivo, sede no operativa o sin cupos")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.crear(dto));
    }

    // GET /api/reservas — Lista todas las reservas del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(summary = "Listar todas las reservas",
            description = "Obtiene la lista completa de reservas registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    // GET /api/reservas/{id} — Obtiene una reserva por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(summary = "Obtener una reserva por id",
            description = "Busca y retorna una reserva según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "400", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único de la reserva")
            @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    // GET /api/reservas/usuario/{usuarioId} — Lista reservas de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física entre bases de datos
    @Operation(summary = "Listar reservas por usuario",
            description = "Obtiene todas las reservas de un usuario específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas del usuario obtenida correctamente")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorUsuario(
            @Parameter(description = "Identificador del usuario (referencia a ms-usuarios)")
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarPorUsuario(usuarioId));
    }

    // GET /api/reservas/turno/{turnoId} — Lista reservas de un turno específico
    // Útil para ver cuántos comensales tienen reserva en un turno
    @Operation(summary = "Listar reservas por turno",
            description = "Obtiene todas las reservas asociadas a un turno disponible específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas del turno obtenida correctamente")
    })
    @GetMapping("/turno/{turnoId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorTurno(
            @Parameter(description = "Identificador del turno disponible")
            @PathVariable Long turnoId) {
        return ResponseEntity.ok(reservaService.listarPorTurno(turnoId));
    }

    // GET /api/reservas/sede/{sedeId} — Lista reservas de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(summary = "Listar reservas por sede",
            description = "Obtiene todas las reservas asociadas a una sede específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas de la sede obtenida correctamente")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorSede(
            @Parameter(description = "Identificador de la sede (referencia a ms-sucursales)")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(reservaService.listarPorSede(sedeId));
    }

    // GET /api/reservas/estado/{estado} — Lista reservas filtradas por estado
    // Ejemplo: GET /api/reservas/estado/ACTIVA o GET /api/reservas/estado/CANCELADA
    @Operation(summary = "Listar reservas por estado",
            description = "Filtra las reservas según su estado: ACTIVA, CANCELADA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas filtrada correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado a filtrar: ACTIVA o CANCELADA")
            @PathVariable String estado) {
        return ResponseEntity.ok(reservaService.listarPorEstado(estado));
    }

    // PATCH /api/reservas/{id}/cancelar — Cancela una reserva existente
    // Usa PATCH porque modifica parcialmente el recurso (solo el estado)
    // Devuelve automáticamente el cupo al turno al cancelar
    @Operation(summary = "Cancelar una reserva",
            description = "Cancela una reserva existente y devuelve el cupo al turno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "400", description = "Reserva no encontrada o ya cancelada")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(
            @Parameter(description = "Identificador único de la reserva a cancelar")
            @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}