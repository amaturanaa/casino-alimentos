package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.service.CategoriaMenuService;
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

// Controlador REST que expone los endpoints del recurso CategoriaMenu
// Las categorías son consumidas por ms-menu via Feign para validar platos
// @Tag agrupa todos los endpoints de este controller bajo "Categorías" en Swagger UI
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de categorías del menú del casino")
public class CategoriaMenuController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final CategoriaMenuService service;

    // POST /api/categorias — Crea una nueva categoría de menú
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear categoría",
            description = "Registra una nueva categoría de menú con estado activo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<CategoriaMenuResponseDTO> crear(
            @Valid @RequestBody CategoriaMenuRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(request));
    }

    // GET /api/categorias — Lista todas las categorías del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar categorías",
            description = "Retorna la lista completa de categorías registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /api/categorias/{id} — Obtiene una categoría por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener categoría por ID",
            description = "Retorna una categoría según su ID. Consumido por ms-menu via Feign"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "400", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMenuResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la categoría a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // PATCH /api/categorias/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?estado=true o ?estado=false
    // Usa PATCH porque modifica parcialmente el recurso
    // Si una categoría se desactiva, ms-menu no podrá crear platos con ella
    @Operation(
            summary = "Cambiar estado de categoría",
            description = "Activa o desactiva una categoría. Si se desactiva, ms-menu no podrá crear platos con ella"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "400", description = "Categoría no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CategoriaMenuResponseDTO> cambiarEstado(
            @Parameter(description = "ID de la categoría")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado (true=activa, false=inactiva)")
            @RequestParam Boolean estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    // GET /api/categorias/estado/{estado} — Filtra categorías por estado
    // @PathVariable extrae el valor booleano desde la URL
    // Ejemplo: GET /api/categorias/estado/true retorna solo categorías activas
    @Operation(
            summary = "Listar categorías por estado",
            description = "Retorna las categorías filtradas según su estado activo/inactivo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista ya filtrada")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado a filtrar (true=activas, false=inactivas)")
            @PathVariable Boolean estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }
}