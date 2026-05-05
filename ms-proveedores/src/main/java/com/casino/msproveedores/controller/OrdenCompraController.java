package com.casino.msproveedores.controller;

import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.service.OrdenCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor

public class OrdenCompraController {
    private final OrdenCompraService ordenCompraService;

    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> crear(
            @Valid @RequestBody OrdenCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCompraService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> listar() {
        return ResponseEntity.ok(ordenCompraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenCompraService.obtenerPorId(id));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorProveedor(
            @PathVariable Long proveedorId) {
        return ResponseEntity.ok(ordenCompraService.listarPorProveedor(proveedorId));
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorSede(
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(ordenCompraService.listarPorSede(sedeId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(ordenCompraService.listarPorEstado(estado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenCompraResponseDTO> cambiarEstado(
            @PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(ordenCompraService.cambiarEstado(id, estado));
    }
}
