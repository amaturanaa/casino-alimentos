package com.casino.msreservas.controller;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.service.TurnoDisponibleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Turnos Disponibles", description = "Gestión de turnos y horarios de comedor disponibles para reservar")
public class TurnoDisponibleController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TurnoDisponibleService turnoService;

    // POST /api/turnos — Crea un nuevo turno disponible
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear un turno disponible",
            description = "Registra un nuevo turno de comedor disponible para reservar")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<TurnoDisponibleResponseDTO> crear(
            @Valid @RequestBody TurnoDisponibleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crear(dto));
    }

    // GET /api/turnos — Lista todos los turnos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(summary = "Listar todos los turnos",
            description = "Obtiene la lista completa de turnos disponibles registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    // GET /api/turnos/{id} — Obtiene un turno por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(summary = "Obtener un turno por id",
            description = "Busca y retorna un turno disponible según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno encontrado"),
            @ApiResponse(responseCode = "400", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDisponibleResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único del turno disponible")
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // GET /api/turnos/sede/{sedeId} — Lista turnos de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(summary = "Listar turnos por sede",
            description = "Obtiene todos los turnos disponibles de una sede específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos de la sede obtenida correctamente")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorSede(
            @Parameter(description = "Identificador de la sede (referencia a ms-sucursales)")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    // GET /api/turnos/fecha/{fecha} — Lista turnos de una fecha específica
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    // Ejemplo: GET /api/turnos/fecha/2026-05-15
    @Operation(summary = "Listar turnos por fecha",
            description = "Obtiene todos los turnos disponibles de una fecha específica (formato yyyy-MM-dd)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos de la fecha obtenida correctamente")
    })
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarPorFecha(
            @Parameter(description = "Fecha a consultar en formato ISO yyyy-MM-dd (ejemplo: 2026-05-15)")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    // GET /api/turnos/disponibles/sede/{sedeId}/fecha/{fecha}
    // Lista turnos con cupos disponibles filtrados por sede y fecha
    // Endpoint principal para que los usuarios vean qué turnos pueden reservar
    @Operation(summary = "Listar turnos con cupos disponibles por sede y fecha",
            description = "Obtiene los turnos que aún tienen cupos disponibles para reservar en una sede y fecha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos disponibles obtenida correctamente")
    })
    @GetMapping("/disponibles/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoDisponibleResponseDTO>> listarDisponibles(
            @Parameter(description = "Identificador de la sede (referencia a ms-sucursales)")
            @PathVariable Long sedeId,
            @Parameter(description = "Fecha a consultar en formato ISO yyyy-MM-dd (ejemplo: 2026-05-15)")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarDisponiblesPorSedeYFecha(sedeId, fecha));
    }
}