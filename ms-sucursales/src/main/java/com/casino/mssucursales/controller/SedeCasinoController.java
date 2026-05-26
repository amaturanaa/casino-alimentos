package com.casino.mssucursales.controller;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.service.SedeCasinoService;
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
public class SedeCasinoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final SedeCasinoService sedeCasinoService;

    // POST /api/sedes — Crea una nueva sede de casino
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @PostMapping
    public ResponseEntity<SedeCasinoResponseDTO> crear(
            @RequestBody SedeCasinoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sedeCasinoService.crearSede(request));
    }

    // GET /api/sedes — Lista todas las sedes del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<SedeCasinoResponseDTO>> listar() {
        return ResponseEntity.ok(sedeCasinoService.listarSedes());
    }

    // GET /api/sedes/{id} — Obtiene una sede por su id
    // Endpoint consumido por múltiples microservicios via Feign
    // para verificar que la sede exista y su estado operativo
    @GetMapping("/{id}")
    public ResponseEntity<SedeCasinoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(sedeCasinoService.obtenerPorId(id));
    }

    // PATCH /api/sedes/{id}/estado — Cambia el estado operativo de una sede
    // @RequestParam recibe el parámetro desde la URL: ?estado=true o ?estado=false
    // Usa PATCH porque modifica parcialmente el recurso
    @PatchMapping("/{id}/estado")
    public ResponseEntity<SedeCasinoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean estado) {
        return ResponseEntity.ok(sedeCasinoService.cambiarEstado(id, estado));
    }

    // GET /api/sedes/estado/{estado} — Lista sedes filtradas por estado operativo
    // Ejemplo: GET /api/sedes/estado/true retorna solo sedes operativas
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SedeCasinoResponseDTO>> listarPorEstado(
            @PathVariable Boolean estado) {
        return ResponseEntity.ok(sedeCasinoService.listarPorEstado(estado));
    }
}