package com.casino.msmenu.controller;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.service.TipoPlatoService;
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

// Controlador REST que expone los endpoints del recurso TipoPlato
// Los tipos de plato clasifican los platos del menú
// Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
// @Tag agrupa todos los endpoints de este controller bajo "Tipos de Plato"
@RestController
@RequestMapping("/api/tipos-plato")
@RequiredArgsConstructor
@Tag(name = "Tipos de Plato",
        description = "Gestión de tipos de plato del menú (Fondo, Entrada, Postre, Bebida)")
public class TipoPlatoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TipoPlatoService tipoPlatoService;

    // POST /api/tipos-plato — Crea un nuevo tipo de plato
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear tipo de plato",
            description = "Registra un nuevo tipo de plato validando nombre único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre ya existe")
    })
    @PostMapping
    public ResponseEntity<TipoPlatoResponseDTO> crear(
            @Valid @RequestBody TipoPlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoPlatoService.crear(dto));
    }

    // GET /api/tipos-plato — Lista todos los tipos de plato del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar tipos de plato",
            description = "Retorna la lista completa de tipos de plato registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista completa de tipos registradas")
    })
    @GetMapping
    public ResponseEntity<List<TipoPlatoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoPlatoService.listar());
    }

    // GET /api/tipos-plato/{id} — Obtiene un tipo de plato por su id
    // @PathVariable extrae el valor {id} desde la URL
    // @NotNull valida que el id no sea nulo
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener tipo de plato por ID",
            description = "Retorna un tipo de plato según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de plato encontrado"),
            @ApiResponse(responseCode = "400", description = "Tipo de plato no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoPlatoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del tipo de plato a buscar")
            @PathVariable @NotNull(message = "El id del tipo de plato es obligatorio")
            Long id) {
        return ResponseEntity.ok(tipoPlatoService.obtenerPorId(id));
    }
}