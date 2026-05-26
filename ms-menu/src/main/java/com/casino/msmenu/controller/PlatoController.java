package com.casino.msmenu.controller;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.service.PlatoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Plato
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Al crear un plato se verifica la categoría via Feign con ms-categorias-menu
@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
public class PlatoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final PlatoService platoService;

    // POST /api/platos — Crea un nuevo plato
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica que la categoría exista y esté activa via Feign con ms-categorias-menu
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crear(
            @Valid @RequestBody PlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(platoService.crear(dto));
    }

    // GET /api/platos — Lista todos los platos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> listar() {
        return ResponseEntity.ok(platoService.listar());
    }

    // GET /api/platos/{id} — Obtiene un plato por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(platoService.obtenerPorId(id));
    }

    // PATCH /api/platos/{id}/disponibilidad — Cambia solo la disponibilidad del plato
    // @RequestParam recibe el parámetro desde la URL: ?disponible=true o ?disponible=false
    // @NotNull valida que el parámetro no sea nulo
    // Usa PATCH porque modifica parcialmente el recurso
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<PlatoResponseDTO> cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam @NotNull(message = "El parámetro 'disponible' es obligatorio")
            Boolean disponible) {
        return ResponseEntity.ok(platoService.cambiarDisponibilidad(id, disponible));
    }

    // GET /api/platos/tipo/{tipoPlatoId} — Lista platos filtrados por tipo
    // @PathVariable extrae el id del tipo de plato desde la URL
    // Ejemplo: listar todos los platos de tipo "Plato de Fondo"
    @GetMapping("/tipo/{tipoPlatoId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorTipo(
            @PathVariable Long tipoPlatoId) {
        return ResponseEntity.ok(platoService.listarPorTipo(tipoPlatoId));
    }

    // GET /api/platos/categoria/{categoriaId} — Lista platos filtrados por categoría
    // @PathVariable extrae el id de la categoría desde la URL
    // categoriaId es referencia a ms-categorias-menu sin FK física
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<PlatoResponseDTO>> listarPorCategoria(
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(platoService.listarPorCategoria(categoriaId));
    }
}