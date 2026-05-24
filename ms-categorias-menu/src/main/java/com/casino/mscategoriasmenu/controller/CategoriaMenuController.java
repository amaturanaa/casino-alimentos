package com.casino.mscategoriasmenu.controller;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.service.CategoriaMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso CategoriaMenu
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Las categorías son consumidas por ms-menu via Feign para validar platos
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaMenuController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final CategoriaMenuService service;

    // POST /api/categorias — Crea una nueva categoría de menú
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<CategoriaMenuResponseDTO> crear(
            @Valid @RequestBody CategoriaMenuRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(request));
    }

    // GET /api/categorias — Lista todas las categorías del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /api/categorias/{id} — Obtiene una categoría por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMenuResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // PATCH /api/categorias/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?estado=true o ?estado=false
    // Usa PATCH porque modifica parcialmente el recurso
    // Si una categoría se desactiva, ms-menu no podrá crear platos con ella
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CategoriaMenuResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    // GET /api/categorias/estado/{estado} — Filtra categorías por estado
    // @PathVariable extrae el valor booleano desde la URL
    // Ejemplo: GET /api/categorias/estado/true retorna solo categorías activas
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CategoriaMenuResponseDTO>> listarPorEstado(
            @PathVariable Boolean estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }
}