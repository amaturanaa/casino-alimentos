package com.casino.mspagos.controller;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<TransaccionResponseDTO> procesarPago(
            @Valid @RequestBody TransaccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionService.procesarPago(dto));
    }

    @GetMapping
    public ResponseEntity<List<TransaccionResponseDTO>> listar() {
        return ResponseEntity.ok(transaccionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.obtenerPorId(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(transaccionService.obtenerPorPedido(pedidoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(transaccionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(transaccionService.listarPorEstado(estado));
    }

    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorMetodo(
            @PathVariable String metodoPago) {
        return ResponseEntity.ok(transaccionService.listarPorMetodo(metodoPago));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionResponseDTO> cambiarEstado(
            @PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(transaccionService.cambiarEstado(id, estado));
    }
}
