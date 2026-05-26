package com.casino.mssucursales.controller;

import com.casino.mssucursales.dto.EmpleadoRequestDTO;
import com.casino.mssucursales.dto.EmpleadoResponseDTO;
import com.casino.mssucursales.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Empleado en ms-sucursales
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Este es el empleado de la sucursal — diferente al empleado de ms-empleados
@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final EmpleadoService empleadoService;

    // POST /api/empleados — Crea un nuevo empleado de sucursal
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.crearEmpleado(dto));
    }

    // GET /api/empleados — Lista todos los empleados de sucursal
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }
}