package com.casino.msmenu.controller;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.service.ProgramacionDiariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
// La programación diaria define qué platos se sirven en cada sede por fecha
// @Tag agrupa todos los endpoints de este controller bajo "Programación Diaria"
@RestController
@RequestMapping("/api/programacion")
@RequiredArgsConstructor
@Tag(name = "Programación Diaria",
        description = "Gestión de la programación de platos por sede y fecha")
public class ProgramacionDiariaController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final ProgramacionDiariaService programacionService;

    // POST /api/programacion — Crea una nueva programación diaria
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear programación diaria",
            description = "Registra qué plato se sirve en una sede y fecha con sus raciones disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Programación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, fecha pasada o plato no encontrado")
    })
    @PostMapping
    public ResponseEntity<ProgramacionDiariaResponseDTO> crear(
            @Valid @RequestBody ProgramacionDiariaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programacionService.crear(dto));
    }

    // GET /api/programacion/fecha/{fecha} — Lista programaciones por fecha
    // @DateTimeFormat convierte el String de la URL a LocalDate formato ISO (yyyy-MM-dd)
    // Ejemplo: GET /api/programacion/fecha/2026-05-15
    @Operation(
            summary = "Listar programaciones por fecha",
            description = "Retorna las programaciones de una fecha específica en formato yyyy-MM-dd"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de programaciones obtenida por fecha")
    })
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFecha(
            @Parameter(description = "Fecha de la programación en formato yyyy-MM-dd")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(programacionService.listarPorFecha(fecha));
    }

    // GET /api/programacion/sede/{sedeId} — Lista programaciones por sede
    // @PathVariable extrae el id de la sede desde la URL
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(
            summary = "Listar programaciones por sede",
            description = "Retorna todas las programaciones de una sede específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de programaciones obtenida por sede")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorSede(
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorSede(sedeId));
    }

    // GET /api/programacion/fecha/{fecha}/sede/{sedeId}
    // Lista programaciones filtradas por fecha y sede simultáneamente
    // Útil para ver el menú del día de una sede específica
    @Operation(
            summary = "Listar programaciones por fecha y sede",
            description = "Retorna el menú del día de una sede específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista del menu del día y de una sede")
    })
    @GetMapping("/fecha/{fecha}/sede/{sedeId}")
    public ResponseEntity<List<ProgramacionDiariaResponseDTO>> listarPorFechaYSede(
            @Parameter(description = "Fecha en formato yyyy-MM-dd")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(programacionService.listarPorFechaYSede(fecha, sedeId));
    }

    // PATCH /api/programacion/{id}/descontar — Descuenta una ración de la programación
    // Se llama cuando un cliente realiza un pedido de ese plato en esa fecha
    // @NotNull valida que el id no sea nulo
    // Retorna la programación actualizada con el nuevo conteo de raciones
    @Operation(
            summary = "Descontar una ración",
            description = "Resta una ración de la programación cuando se realiza un pedido. Retorna error 400 controlado si no hay raciones disponibles"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ración descontada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Programación no encontrada o sin raciones disponibles")
    })
    @PatchMapping("/{id}/descontar")
    public ResponseEntity<ProgramacionDiariaResponseDTO> descontarRacion(
            @Parameter(description = "ID de la programación")
            @PathVariable @NotNull(message = "El id de la programación es obligatorio")
            Long id) {
        return ResponseEntity.ok(programacionService.descontarRacion(id));
    }
}