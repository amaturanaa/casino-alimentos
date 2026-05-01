package com.casino.mssucursales.controller;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import com.casino.mssucursales.service.EmpleadoSedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado_sede")
@RequiredArgsConstructor
public class EmpleadoSedeController {

    private final EmpleadoSedeService service;


    @PostMapping
    public ResponseEntity<EmpleadoSedeResponseDTO> asignar(
            @RequestBody EmpleadoSedeRequestDTO request) {

        return ResponseEntity.ok(service.asignarEmpleadoASede(request));
    }


    @DeleteMapping
    public ResponseEntity<Void> eliminar(
            @RequestParam Long idEmpleado,
            @RequestParam Long idSedeCasino) {

        service.eliminarAsignacion(idEmpleado, idSedeCasino);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sede/{idSedeCasino}")
    public ResponseEntity<List<EmpleadoSedeResponseDTO>> listarPorSede(
            @PathVariable Long idSedeCasino) {

        return ResponseEntity.ok(service.listarPorSede(idSedeCasino));
    }


    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<EmpleadoSedeResponseDTO>> listarPorEmpleado(
            @PathVariable Long idEmpleado) {

        return ResponseEntity.ok(service.listarPorEmpleado(idEmpleado));
    }
}
