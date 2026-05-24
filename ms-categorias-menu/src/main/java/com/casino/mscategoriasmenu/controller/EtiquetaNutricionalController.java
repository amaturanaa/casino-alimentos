package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.service.EtiquetaNutricionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso EtiquetaNutricional
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Cada categoría de menú puede tener una etiqueta nutricional asociada (relación 1 a 1)
@RestController
@RequestMapping("/api/etiquetas")
@RequiredArgsConstructor
public class EtiquetaNutricionalController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final EtiquetaNutricionalService etiquetaService;

    // POST /api/etiquetas — Crea una nueva etiqueta nutricional
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    // Valida que la categoría no tenga ya una etiqueta asociada
    @PostMapping
    public ResponseEntity<EtiquetaNutricionalResponseDTO> crear(
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(etiquetaService.crear(dto));
    }

    // GET /api/etiquetas — Lista todas las etiquetas nutricionales del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<EtiquetaNutricionalResponseDTO>> listar() {
        return ResponseEntity.ok(etiquetaService.listar());
    }

    // GET /api/etiquetas/categoria/{categoriaId} — Obtiene etiqueta por categoría
    // @PathVariable extrae el id de la categoría desde la URL
    // Relación 1 a 1: cada categoría tiene como máximo una etiqueta nutricional
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> obtenerPorCategoria(
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(etiquetaService.obtenerPorCategoria(categoriaId));
    }

    // PUT /api/etiquetas/{id} — Actualiza todos los datos de una etiqueta
    // @Valid valida el DTO antes de procesar
    // Usa PUT porque reemplaza completamente el recurso
    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaNutricionalResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EtiquetaNutricionalRequestDTO dto) {
        return ResponseEntity.ok(etiquetaService.actualizar(id, dto));
    }
}