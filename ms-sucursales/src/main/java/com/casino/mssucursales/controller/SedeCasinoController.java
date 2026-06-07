package com.casino.mssucursales.controller;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.service.SedeCasinoService;
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

// Controlador REST que expone los endpoints del recurso SedeCasino
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// ms-sucursales es consumido por múltiples microservicios via Feign:
// ms-inventario, ms-proveedores, ms-empleados, ms-reservas, ms-pedidos
@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
@Tag(name = "Sedes de Casino", description = "Gestión de las sedes del casino de alimentos")
public class SedeCasinoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final SedeCasinoService sedeCasinoService;

    // POST /api/sedes — Crea una nueva sede de casino
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear una sede",
            description = "Registra una nueva sede de casino en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sede creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<SedeCasinoResponseDTO> crear(
            @Valid @RequestBody SedeCasinoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sedeCasinoService.crearSede(request));
    }

    // GET /api/sedes — Lista todas las sedes del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(summary = "Listar todas las sedes",
            description = "Obtiene la lista completa de sedes registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de sedes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<SedeCasinoResponseDTO>> listar() {
        return ResponseEntity.ok(sedeCasinoService.listarSedes());
    }

    // GET /api/sedes/{id} — Obtiene una sede por su id
    // Endpoint consumido por múltiples microservicios via Feign
    // para verificar que la sede exista y su estado operativo
    @Operation(summary = "Obtener una sede por id",
            description = "Busca y retorna una sede según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede encontrada"),
            @ApiResponse(responseCode = "400", description = "Sede no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SedeCasinoResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único de la sede")
            @PathVariable Long id) {
        return ResponseEntity.ok(sedeCasinoService.obtenerPorId(id));
    }

    // PATCH /api/sedes/{id}/estado — Cambia el estado operativo de una sede
    // @RequestParam recibe el parámetro desde la URL: ?estado=true o ?estado=false
    // Usa PATCH porque modifica parcialmente el recurso
    @Operation(summary = "Cambiar el estado operativo de una sede",
            description = "Activa o desactiva una sede según el estado enviado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de la sede actualizado"),
            @ApiResponse(responseCode = "400", description = "Sede no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<SedeCasinoResponseDTO> cambiarEstado(
            @Parameter(description = "Identificador único de la sede")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado operativo: true (operativa) o false (no operativa)")
            @RequestParam Boolean estado) {
        return ResponseEntity.ok(sedeCasinoService.cambiarEstado(id, estado));
    }

    // GET /api/sedes/estado/{estado} — Lista sedes filtradas por estado operativo
    // Ejemplo: GET /api/sedes/estado/true retorna solo sedes operativas
    @Operation(summary = "Listar sedes por estado",
            description = "Filtra las sedes según su estado operativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de sedes filtrada correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SedeCasinoResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado operativo a filtrar: true (operativas) o false (no operativas)")
            @PathVariable Boolean estado) {
        return ResponseEntity.ok(sedeCasinoService.listarPorEstado(estado));
    }
}