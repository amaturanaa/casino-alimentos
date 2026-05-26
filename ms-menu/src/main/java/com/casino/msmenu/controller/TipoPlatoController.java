package com.casino.msmenu.controller;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.service.TipoPlatoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso TipoPlato
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Los tipos de plato clasifican los platos del menú
// Ejemplo: "Plato de Fondo", "Entrada", "Postre", "Bebida"
@RestController
@RequestMapping("/api/tipos-plato")
@RequiredArgsConstructor
public class TipoPlatoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TipoPlatoService tipoPlatoService;

    // POST /api/tipos-plato — Crea un nuevo tipo de plato
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<TipoPlatoResponseDTO> crear(
            @Valid @RequestBody TipoPlatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoPlatoService.crear(dto));
    }

    // GET /api/tipos-plato — Lista todos los tipos de plato del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<TipoPlatoResponseDTO>> listar() {
        return ResponseEntity.ok(tipoPlatoService.listar());
    }

    // GET /api/tipos-plato/{id} — Obtiene un tipo de plato por su id
    // @PathVariable extrae el valor {id} desde la URL
    // @NotNull valida que el id no sea nulo
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<TipoPlatoResponseDTO> obtenerPorId(
            @PathVariable @NotNull(message = "El id del tipo de plato es obligatorio")
            Long id) {
        return ResponseEntity.ok(tipoPlatoService.obtenerPorId(id));
    }
}