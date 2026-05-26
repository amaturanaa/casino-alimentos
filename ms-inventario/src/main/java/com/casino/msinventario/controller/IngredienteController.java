package com.casino.msinventario.controller;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.service.IngredienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Ingrediente
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Al crear un ingrediente se verifica la sede via Feign con ms-sucursales
@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
public class IngredienteController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final IngredienteService ingredienteService;

    // POST /api/ingredientes — Crea un nuevo ingrediente
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Verifica sede operativa mediante comunicación Feign con ms-sucursales
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crear(
            @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ingredienteService.crear(dto));
    }

    // GET /api/ingredientes — Lista todos los ingredientes del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(ingredienteService.listar());
    }

    // GET /api/ingredientes/{id} — Obtiene un ingrediente por su id
    // @PathVariable extrae el valor {id} desde la URL
    // @Min valida que el id sea mayor a 0 directamente en el parámetro
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(
            @PathVariable @Min(value = 1, message = "El id debe ser mayor a 0") Long id) {
        return ResponseEntity.ok(ingredienteService.obtenerPorId(id));
    }

    // GET /api/ingredientes/sede/{sedeId} — Lista ingredientes de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<IngredienteResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarPorSede(sedeId));
    }

    // GET /api/ingredientes/stock-bajo — Lista ingredientes con stock bajo
    // Un ingrediente tiene stock bajo cuando stockActual <= stockMinimo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajo() {
        return ResponseEntity.ok(ingredienteService.listarStockBajo());
    }

    // GET /api/ingredientes/sede/{sedeId}/stock-bajo
    // Lista ingredientes con stock bajo filtrados por sede específica
    @GetMapping("/sede/{sedeId}/stock-bajo")
    public ResponseEntity<List<IngredienteResponseDTO>> listarStockBajoPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ingredienteService.listarStockBajoPorSede(sedeId));
    }

    // PUT /api/ingredientes/{id} — Actualiza todos los datos de un ingrediente
    // @Valid valida el DTO antes de procesar
    // Usa PUT porque reemplaza completamente el recurso
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(ingredienteService.actualizar(id, dto));
    }
}