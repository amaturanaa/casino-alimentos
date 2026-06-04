package com.casino.msempleados.controller;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Empleado
// @Tag agrupa todos los endpoints de este controller bajo "Empleados" en Swagger UI
@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Tag(name = "Empleados", description = "Gestión de empleados del casino")
public class EmpleadoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final EmpleadoService empleadoService;

    // POST /api/empleados — Crea un nuevo empleado
    // @Valid activa Bean Validation sobre el DTO antes de procesar la solicitud
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(
            summary = "Crear empleado",
            description = "Registra un nuevo empleado validando RUT único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o RUT ya existe")
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.crear(dto));
    }

    // GET /api/empleados — Lista todos los empleados del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar empleados",
            description = "Retorna la lista completa de empleados registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida")
    })
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    // GET /api/empleados/activos — Lista solo empleados con estado activo = true
    @Operation(
            summary = "Listar empleados activos",
            description = "Retorna solo los empleados con estado activo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empleados activos de los casinos")
    })
    @GetMapping("/activos")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(empleadoService.listarActivos());
    }

    // GET /api/empleados/{id} — Obtiene un empleado por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener empleado por ID",
            description = "Retorna un empleado según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "400", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del empleado a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    // GET /api/empleados/rut/{rut} — Obtiene un empleado por su RUT
    @Operation(
            summary = "Obtener empleado por RUT",
            description = "Retorna un empleado según su RUT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "400", description = "Empleado no encontrado")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorRut(
            @Parameter(description = "RUT del empleado a buscar")
            @PathVariable String rut) {
        return ResponseEntity.ok(empleadoService.obtenerPorRut(rut));
    }

    // GET /api/empleados/cargo/{cargo} — Lista empleados filtrados por cargo
    @Operation(
            summary = "Listar empleados por cargo",
            description = "Retorna los empleados que tienen un cargo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida por cargo")
    })
    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarPorCargo(
            @Parameter(description = "Cargo a filtrar (ej: Cocinero, Cajero)")
            @PathVariable String cargo) {
        return ResponseEntity.ok(empleadoService.listarPorCargo(cargo));
    }

    // PUT /api/empleados/{id} — Actualiza todos los datos de un empleado
    // @Valid valida el DTO antes de procesar
    @Operation(
            summary = "Actualizar empleado",
            description = "Reemplaza todos los datos de un empleado existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Empleado no encontrado o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @Parameter(description = "ID del empleado a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    // PATCH /api/empleados/{id}/estado — Cambia solo el estado activo/inactivo
    // @RequestParam recibe el parámetro desde la URL: ?activo=true o ?activo=false
    // Usa PATCH porque modifica parcialmente el recurso
    @Operation(
            summary = "Cambiar estado de empleado",
            description = "estado activo o inactivo  de un empleado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "400", description = "Empleado no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoResponseDTO> cambiarEstado(
            @Parameter(description = "ID del empleado")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado (true=activo, false=inactivo)")
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(empleadoService.cambiarEstado(id, activo));
    }
}