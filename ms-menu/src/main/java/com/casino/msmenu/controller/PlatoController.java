package com.casino.msmenu.controller;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Plato
// Al crear un plato se verifica la categoría via Feign con ms-categorias-menu
// @Tag agrupa todos los endpoints de este controller bajo "Platos"
@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
@Tag(name = "Platos", description = "Gestión de platos del menú del casino")
public class PlatoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final PlatoService platoService;

    // POST /api/platos — Crea un nuevo plato
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica que la categoría exista y esté activa via Feign con ms-categorias-menu
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear plato",
            description = "Registra un nuevo plato. Verifica via Feign que la categoría exista y esté activa en ms-categorias-menu"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría inactiva")
    })
    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crear(
            @Valid @RequestBody PlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(platoService.crear(dto));
    }

    // GET /api/platos — Lista todos los platos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar platos",
            description = "Retorna la lista completa de platos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de platos registrados")
    })
    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> listar() {
        return ResponseEntity.ok(platoService.listar());
    }

    // GET /api/platos/{id} — Obtiene un plato por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener plato por ID",
            description = "Retorna un plato según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "400", description = "Plato no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del plato a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(platoService.obtenerPorId(id));
    }

    // PATCH /api/platos/{id}/disponibilidad — Cambia solo la disponibilidad del plato
    // @RequestParam recibe el parámetro desde la URL: ?disponible=true o ?disponible=false
    // @NotNull valida que el parámetro no sea nulo
    // Usa PATCH porque modifica parcialmente el recurso
    @Operation(
            summary = "Cambiar disponibilidad del plato",
            description = "Marca un plato como disponible o no disponible para pedidos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "400", description = "Plato no encontrado o parámetro inválido")
    })
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<PlatoResponseDTO> cambiarDisponibilidad(
            @Parameter(description = "ID del plato")
            @PathVariable Long id,
            @Parameter(description = "Nueva disponibilidad (true=disponible, false=no disponible)")
            @RequestParam @NotNull(message = "El parámetro 'disponible' es obligatorio")
            Boolean disponible) {
        return ResponseEntity.ok(platoService.cambiarDisponibilidad(id, disponible));
    }

    // GET /api/platos/tipo/{tipoPlatoId} — Lista platos filtrados por tipo
    // @PathVariable extrae el id del tipo de plato desde la URL
    // Ejemplo: listar todos los platos de tipo "Plato de Fondo"
    @Operation(
            summary = "Listar platos por tipo",
            description = "Retorna los platos de un tipo específico (ej: Plato de Fondo, Entrada, Postre)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista completa según el tiempo en el menú")
    })
    @GetMapping("/tipo/{tipoPlatoId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorTipo(
            @Parameter(description = "ID del tipo de plato")
            @PathVariable Long tipoPlatoId) {
        return ResponseEntity.ok(platoService.listarPorTipo(tipoPlatoId));
    }

    // GET /api/platos/categoria/{categoriaId} — Lista platos filtrados por categoría
    // @PathVariable extrae el id de la categoría desde la URL
    // categoriaId es referencia a ms-categorias-menu sin FK física
    @Operation(
            summary = "Listar platos por categoría",
            description = "Retorna los platos de una categoría específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista por id del nombre del plato")
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorCategoria(
            @Parameter(description = "ID de la categoría")
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(platoService.listarPorCategoria(categoriaId));
    }
}