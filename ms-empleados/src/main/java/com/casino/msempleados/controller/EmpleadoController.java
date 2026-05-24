package com.casino.msempleados.controller;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Empleado
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final EmpleadoService empleadoService;

    // POST /api/empleados — Crea un nuevo empleado
    // @Valid activa Bean Validation sobre el DTO antes de procesar la solicitud
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.crear(dto));
    }

    // GET /api/empleados — Lista todos los empleados del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    // GET /api/empleados/activos — Lista solo empleados con estado activo = true
    @GetMapping("/activos")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(empleadoService.listarActivos());
    }

    // GET /api/empleados/{id} — Obtiene un empleado por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    // GET /api/empleados/rut/{rut} — Obtiene un empleado por su RUT
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorRut(
            @PathVariable String rut) {
        return ResponseEntity.ok(empleadoService.obtenerPorRut(rut));
    }

    // GET /api/empleados/cargo/{cargo} — Lista empleados filtrados por cargo
    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarPorCargo(
            @PathVariable String cargo) {
        return ResponseEntity.ok(empleadoService.listarPorCargo(cargo));
    }

    // PUT /api/empleados/{id} — Actualiza todos los datos de un empleado
    // @Valid valida el DTO antes de procesar
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    // PATCH /api/empleados/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?activo=true o ?activo=false
    // Usa PATCH porque modifica parcialmente el recurso
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(empleadoService.cambiarEstado(id, activo));
    }
}