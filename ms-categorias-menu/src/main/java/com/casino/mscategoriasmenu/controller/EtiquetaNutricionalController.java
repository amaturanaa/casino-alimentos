package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.service.EtiquetaNutricionalService;
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

// Controlador REST que expone los endpoints del recurso EtiquetaNutricional
// Cada categoría de menú puede tener una etiqueta nutricional asociada (relación 1 a 1)
// @Tag agrupa todos los endpoints de este controller bajo "Etiquetas Nutricionales"
@RestController
@RequestMapping("/api/etiquetas")
@RequiredArgsConstructor
@Tag(name = "Etiquetas Nutricionales",
        description = "Gestión de etiquetas nutricionales asociadas a categorías de Menús")
public class EtiquetaNutricionalController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final EtiquetaNutricionalService etiquetaService;

    // POST /api/etiquetas — Crea una nueva etiqueta nutricional
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    // Valida que la categoría no tenga ya una etiqueta asociada
    @Operation(
            summary = "Crear etiqueta nutricional",
            description = "Registra una nueva etiqueta nutricional para una categoría. Valida que la categoría no tenga ya una etiqueta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Etiqueta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o la categoría ya tiene etiqueta")
    })
    @PostMapping
    public ResponseEntity<EtiquetaNutricionalResponseDTO> crear(
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(etiquetaService.crear(dto));
    }

    // GET /api/etiquetas — Lista todas las etiquetas nutricionales del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar etiquetas nutricionales",
            description = "Retorna la lista completa de etiquetas nutricionales registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de etiquetas obtenida")
    })
    @GetMapping
    public ResponseEntity<List<EtiquetaNutricionalResponseDTO>> listar() {
        return ResponseEntity.ok(etiquetaService.listar());
    }

    // GET /api/etiquetas/categoria/{categoriaId} — Obtiene etiqueta por categoría
    // @PathVariable extrae el id de la categoría desde la URL
    // Relación 1 a 1: cada categoría tiene como máximo una etiqueta nutricional
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener etiqueta por categoría",
            description = "Retorna la etiqueta nutricional asociada a una categoría específica "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta encontrada"),
            @ApiResponse(responseCode = "400", description = "Etiqueta no encontrada para esa categoría")
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> obtenerPorCategoria(
            @Parameter(description = "ID de la categoría asociada a la etiqueta")
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(etiquetaService.obtenerPorCategoria(categoriaId));
    }

    // PUT /api/etiquetas/{id} — Actualiza todos los datos de una etiqueta
    // @Valid valida el DTO antes de procesar
    // Usa PUT porque reemplaza completamente el recurso
    @Operation(
            summary = "Actualizar etiqueta nutricional",
            description = "Reemplaza todos los datos de una etiqueta nutricional existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta actualizada"),
            @ApiResponse(responseCode = "400", description = "Etiqueta no encontrada o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> actualizar(
            @Parameter(description = "ID de la etiqueta a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.ok(etiquetaService.actualizar(id, dto));
    }
}