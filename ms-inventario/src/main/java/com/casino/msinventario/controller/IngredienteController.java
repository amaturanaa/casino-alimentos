package com.casino.msinventario.controller;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Ingrediente
// Al crear un ingrediente se verifica la sede via Feign con ms-sucursales
// @Tag agrupa todos los endpoints de este controller bajo "Ingredientes"
@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
@Tag(name = "Ingredientes", description = "Gestión de ingredientes del inventario del casino")
public class IngredienteController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final IngredienteService ingredienteService;

    // POST /api/ingredientes — Crea un nuevo ingrediente
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear ingrediente",
            description = "Registra un nuevo ingrediente. Verifica via Feign que la sede exista y esté operativa en ms-sucursales"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o sede no operativa")
    })
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crear(
            @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ingredienteService.crear(dto));
    }

    // GET /api/ingredientes — Lista todos los ingredientes del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar ingredientes",
            description = "Retorna la lista completa de ingredientes registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista completa de ingredientes")
    })
    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(ingredienteService.listar());
    }

    // GET /api/ingredientes/{id} — Obtiene un ingrediente por su id
    // @PathVariable extrae el valor {id} desde la URL
    // @Min valida que el id sea mayor a 0 directamente en el parámetro
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener ingrediente por ID",
            description = "Retorna un ingrediente según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado"),
            @ApiResponse(responseCode = "400", description = "Ingrediente no encontrado o ID inválido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(
            @Parameter(description = "ID del ingrediente a buscar (debe ser mayor a 0)")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor a 0") Long id) {
        return ResponseEntity.ok(ingredienteService.obtenerPorId(id));
    }

    // GET /api/ingredientes/sede/{sedeId} — Lista ingredientes de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(
            summary = "Listar ingredientes por sede",
            description = "Retorna los ingredientes almacenados en una sede específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingredientes según sede")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<IngredienteResponseDTO>> listarPorSede(
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarPorSede(sedeId));
    }

    // GET /api/ingredientes/stock-bajo — Lista ingredientes con stock bajo
    // Un ingrediente tiene stock bajo cuando stockActual <= stockMinimo
    @Operation(
            summary = "Listar ingredientes con stock bajo",
            description = "Retorna los ingredientes donde stockActual es menor o igual al stockMinimo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingredientes con stock bajo")
    })
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajo() {
        return ResponseEntity.ok(ingredienteService.listarStockBajo());
    }

    // GET /api/ingredientes/sede/{sedeId}/stock-bajo
    // Lista ingredientes con stock bajo filtrados por sede específica
    @Operation(
            summary = "Listar ingredientes con stock bajo por sede",
            description = "Retorna los ingredientes con stock bajo de una sede específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de una sede con ingredientes que tienen stock bajo")
    })
    @GetMapping("/sede/{sedeId}/stock-bajo")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajoPorSede(
            @Parameter(description = "ID de la sede")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarStockBajoPorSede(sedeId));
    }

    // PUT /api/ingredientes/{id} — Actualiza todos los datos de un ingrediente
    // @Valid valida el DTO antes de procesar
    // Usa PUT porque reemplaza completamente el recurso
    @Operation(
            summary = "Actualizar ingrediente",
            description = "Reemplaza todos los datos de un ingrediente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente actualizado"),
            @ApiResponse(responseCode = "400", description = "Ingrediente no encontrado o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(
            @Parameter(description = "ID del ingrediente a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(ingredienteService.actualizar(id, dto));
    }
}