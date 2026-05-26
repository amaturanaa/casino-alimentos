package com.casino.mssucursales.controller;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import com.casino.mssucursales.service.EmpleadoSedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints de la relación EmpleadoSede
// Gestiona la asignación de empleados a sedes del casino
// Usa clave primaria compuesta (idEmpleado + idSedeCasino)
@RestController
@RequestMapping("/api/empleado-sede")
@RequiredArgsConstructor
public class EmpleadoSedeController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    private final EmpleadoSedeService service;

    // POST /api/empleado-sede — Asigna un empleado a una sede
    // Crea la relación entre empleado y sede en la tabla intermedia
    @PostMapping
    public ResponseEntity<EmpleadoSedeResponseDTO> asignar(
            @RequestBody EmpleadoSedeRequestDTO request) {
        return ResponseEntity.ok(service.asignarEmpleadoASede(request));
    }

    // DELETE /api/empleado-sede — Elimina la asignación de un empleado a una sede
    // @RequestParam recibe los ids desde los parámetros de la URL
    // ResponseEntity.noContent() retorna código HTTP 204 sin cuerpo
    @DeleteMapping
    public ResponseEntity<Void> eliminar(
            @RequestParam Long idEmpleado,
            @RequestParam Long idSedeCasino) {
        service.eliminarAsignacion(idEmpleado, idSedeCasino);
        return ResponseEntity.noContent().build();
    }

    // GET /api/empleado-sede/sede/{idSedeCasino} — Lista empleados de una sede
    // Útil para ver qué empleados trabajan en una sede específica
    @GetMapping("/sede/{idSedeCasino}")
    public ResponseEntity<List<EmpleadoSedeResponseDTO>> listarPorSede(
            @PathVariable Long idSedeCasino) {
        return ResponseEntity.ok(service.listarPorSede(idSedeCasino));
    }

    // GET /api/empleado-sede/empleado/{idEmpleado} — Lista sedes de un empleado
    // Útil para ver en qué sedes trabaja un empleado específico
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<EmpleadoSedeResponseDTO>> listarPorEmpleado(
            @PathVariable Long idEmpleado) {
        return ResponseEntity.ok(service.listarPorEmpleado(idEmpleado));
    }
}