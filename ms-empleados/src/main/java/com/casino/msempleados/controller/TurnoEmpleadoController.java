package com.casino.msempleados.controller;

import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import com.casino.msempleados.service.TurnoEmpleadoService;
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

// Controlador REST que expone los endpoints del recurso TurnoEmpleado
// @Tag agrupa todos los endpoints de este controller bajo "Turnos de Empleado"
@RestController
@RequestMapping("/api/turnos-empleado")
@RequiredArgsConstructor
@Tag(name = "Turnos de Empleados",
        description = "Gestión de turnos de trabajo de empleados por sede")
public class TurnoEmpleadoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TurnoEmpleadoService turnoService;

    // POST /api/turnos-empleado — Crea un nuevo turno para un empleado
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear turno de empleado",
            description = "Registra un nuevo turno. Verifica via Feign que la sede exista y esté operativa en ms-sucursales"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Empleado no encontrado, sede no operativa o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<TurnoEmpleadoResponseDTO> crear(
            @Valid @RequestBody TurnoEmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crear(dto));
    }

    // GET /api/turnos-empleado — Lista todos los turnos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar turnos",
            description = "Retorna la lista completa de turnos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida")
    })
    @GetMapping
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    // GET /api/turnos-empleado/{id} — Obtiene un turno por su id
    // @PathVariable extrae el valor {id} desde la URL
    @Operation(
            summary = "Obtener turno por ID",
            description = "Retorna un turno según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno encontrado"),
            @ApiResponse(responseCode = "400", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoEmpleadoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del turno a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    // GET /api/turnos-empleado/empleado/{idEmpleado} — Lista turnos de un empleado
    @Operation(
            summary = "Listar turnos por empleado",
            description = "Retorna todos los turnos asignados a un empleado específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turnos del empleado")
    })
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorEmpleado(
            @Parameter(description = "ID del empleado")
            @PathVariable Long idEmpleado) {
        return ResponseEntity.ok(turnoService.listarPorEmpleado(idEmpleado));
    }

    // GET /api/turnos-empleado/sede/{sedeId} — Lista turnos de una sede
    @Operation(
            summary = "Listar turnos por sede",
            description = "Retorna todos los turnos de una sede específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turnos de la sede")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSede(
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(turnoService.listarPorSede(sedeId));
    }

    // GET /api/turnos-empleado/fecha/{fecha} — Lista turnos de una fecha específica
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    @Operation(
            summary = "Listar turnos por fecha",
            description = "Retorna los turnos de una fecha específica en formato yyyy-MM-dd"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turnos según fecha")
    })
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorFecha(
            @Parameter(description = "Fecha del turno en formato yyyy-MM-dd")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorFecha(fecha));
    }

    // GET /api/turnos-empleado/sede/{sedeId}/fecha/{fecha}
    // Lista turnos filtrados por sede y fecha simultáneamente
    @Operation(
            summary = "Listar turnos por sede y fecha",
            description = "Retorna los turnos filtrados por sede y fecha simultáneamente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida para sede y fecha definida")
    })
    @GetMapping("/sede/{sedeId}/fecha/{fecha}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorSedeYFecha(
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId,
            @Parameter(description = "Fecha del turno en formato yyyy-MM-dd")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.listarPorSedeYFecha(sedeId, fecha));
    }

    // GET /api/turnos-empleado/tipo/{tipoTurno} — Lista turnos por tipo
    // Ejemplo: MAÑANA, TARDE, NOCHE
    @Operation(
            summary = "Listar turnos por tipo",
            description = "Retorna los turnos de un tipo específico (MAÑANA, TARDE, NOCHE)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista del turno")
    })
    @GetMapping("/tipo/{tipoTurno}")
    public ResponseEntity<List<TurnoEmpleadoResponseDTO>> listarPorTipo(
            @Parameter(description = "Tipo de turno (MAÑANA, TARDE, NOCHE)")
            @PathVariable String tipoTurno) {
        return ResponseEntity.ok(turnoService.listarPorTipo(tipoTurno));
    }

    // DELETE /api/turnos-empleado/{id} — Elimina un turno por su id
    // ResponseEntity.noContent() retorna código HTTP 204 sin cuerpo de respuesta
    // 204 indica éxito en la operación pero sin contenido que retornar
    @Operation(
            summary = "Eliminar turno",
            description = "Elimina un turno por su ID. No retorna contenido en la respuesta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turno eliminado exitosamente, sin contenido"),
            @ApiResponse(responseCode = "400", description = "Turno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del turno a eliminar")
            @PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}