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

// Controlador REST que expone los endpoints del recurso Transaccion (pagos)
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Gestiona el procesamiento de pagos y consulta de transacciones del sistema
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class TransaccionController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TransaccionService transaccionService;

    // POST /api/pagos — Procesa un nuevo pago
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Valida que el método de pago sea válido y que no exista ya un pago para ese pedido
    // HttpStatus.CREATED (201) indica creación exitosa de una nueva transacción
    @PostMapping
    public ResponseEntity<TransaccionResponseDTO> procesarPago(
            @Valid @RequestBody TransaccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionService.procesarPago(dto));
    }

    // GET /api/pagos — Lista todas las transacciones del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<TransaccionResponseDTO>> listar() {
        return ResponseEntity.ok(transaccionService.listar());
    }

    // GET /api/pagos/{id} — Obtiene una transacción por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.obtenerPorId(id));
    }

    // GET /api/pagos/pedido/{pedidoId} — Obtiene la transacción de un pedido específico
    // Relación 1 a 1: cada pedido tiene como máximo una transacción de pago
    // pedidoId es referencia a ms-pedidos sin FK física entre bases de datos
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorPedido(
            @PathVariable Long pedidoId) {
        return ResponseEntity.ok(transaccionService.obtenerPorPedido(pedidoId));
    }

    // GET /api/pagos/usuario/{usuarioId} — Lista transacciones de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física entre bases de datos
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(transaccionService.listarPorUsuario(usuarioId));
    }

    // GET /api/pagos/estado/{estado} — Lista transacciones filtradas por estado
    // Ejemplo: GET /api/pagos/estado/PENDIENTE o GET /api/pagos/estado/APROBADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(transaccionService.listarPorEstado(estado));
    }

    // GET /api/pagos/metodo/{metodoPago} — Lista transacciones filtradas por método de pago
    // Ejemplo: GET /api/pagos/metodo/TARJETA_CREDITO o GET /api/pagos/metodo/JUNAEB
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorMetodo(
            @PathVariable String metodoPago) {
        return ResponseEntity.ok(transaccionService.listarPorMetodo(metodoPago));
    }

    // PATCH /api/pagos/{id}/estado — Cambia el estado de una transacción
    // @RequestParam recibe el parámetro desde la URL: ?estado=APROBADO
    // Usa PATCH porque modifica parcialmente el recurso
    // Estados válidos: PENDIENTE, APROBADO, RECHAZADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(transaccionService.cambiarEstado(id, estado));
    }
}