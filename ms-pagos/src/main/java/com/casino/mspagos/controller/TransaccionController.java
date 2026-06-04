package com.casino.mspagos.controller;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.service.TransaccionService;
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

// Controlador REST que expone los endpoints del recurso Transaccion (pagos)
// Gestiona el procesamiento de pagos y consulta de transacciones del sistema
// @Tag agrupa todos los endpoints de este controller bajo "Pagos"
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Procesamiento de pagos y gestión de transacciones del casino")
public class TransaccionController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final TransaccionService transaccionService;

    // POST /api/pagos — Procesa un nuevo pago
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // Valida que el método de pago sea válido y que no exista ya un pago para ese pedido
    // HttpStatus.CREATED (201) indica creación exitosa de una nueva transacción
    @Operation(
            summary = "Procesar pago",
            description = "Registra un nuevo pago. Verifica via Feign que el pedido exista en ms-pedidos, que pertenezca al usuario y que no tenga ya un pago"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado, pago duplicado, método inválido o datos incorrectos")
    })
    @PostMapping
    public ResponseEntity<TransaccionResponseDTO> procesarPago(
            @Valid @RequestBody TransaccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionService.procesarPago(dto));
    }

    // GET /api/pagos — Lista todas las transacciones del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(
            summary = "Listar transacciones",
            description = "Retorna la lista completa de transacciones registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones registradas")
    })
    @GetMapping
    public ResponseEntity<List<TransaccionResponseDTO>> listar() {
        return ResponseEntity.ok(transaccionService.listar());
    }

    // GET /api/pagos/{id} — Obtiene una transacción por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(
            summary = "Obtener transacción por ID",
            description = "Retorna una transacción según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
            @ApiResponse(responseCode = "400", description = "Transacción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la transacción a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.obtenerPorId(id));
    }

    // GET /api/pagos/pedido/{pedidoId} — Obtiene la transacción de un pedido específico
    // Relación 1 a 1: cada pedido tiene como máximo una transacción de pago
    // pedidoId es referencia a ms-pedidos sin FK física entre bases de datos
    @Operation(
            summary = "Obtener transacción por pedido",
            description = "Retorna la transacción asociada a un pedido específico "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
            @ApiResponse(responseCode = "400", description = "No existe transacción para ese pedido")
    })
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorPedido(
            @Parameter(description = "ID del pedido")
            @PathVariable Long pedidoId) {
        return ResponseEntity.ok(transaccionService.obtenerPorPedido(pedidoId));
    }

    // GET /api/pagos/usuario/{usuarioId} — Lista transacciones de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física entre bases de datos
    @Operation(
            summary = "Listar transacciones por usuario",
            description = "Retorna todas las transacciones de un usuario específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones del usuario")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorUsuario(
            @Parameter(description = "ID del usuario")
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(transaccionService.listarPorUsuario(usuarioId));
    }

    // GET /api/pagos/estado/{estado} — Lista transacciones filtradas por estado
    // Ejemplo: GET /api/pagos/estado/PENDIENTE o GET /api/pagos/estado/APROBADO
    @Operation(
            summary = "Listar transacciones por estado",
            description = "Retorna las transacciones de un estado específico (PENDIENTE, APROBADO, RECHAZADO)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista según estado")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado a filtrar (PENDIENTE, APROBADO, RECHAZADO)")
            @PathVariable String estado) {
        return ResponseEntity.ok(transaccionService.listarPorEstado(estado));
    }

    // GET /api/pagos/metodo/{metodoPago} — Lista transacciones filtradas por método de pago
    // Ejemplo: GET /api/pagos/metodo/TARJETA_CREDITO o GET /api/pagos/metodo/JUNAEB
    @Operation(
            summary = "Listar transacciones por método de pago",
            description = "Retorna las transacciones de un método específico (TARJETA_CREDITO, JUNAEB, SUBSIDIO_EMPRESA, EFECTIVO)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista según metodo de pago")
    })
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarPorMetodo(
            @Parameter(description = "Método de pago a filtrar")
            @PathVariable String metodoPago) {
        return ResponseEntity.ok(transaccionService.listarPorMetodo(metodoPago));
    }

    // PATCH /api/pagos/{id}/estado — Cambia el estado de una transacción
    // @RequestParam recibe el parámetro desde la URL: ?estado=APROBADO
    // Usa PATCH porque modifica parcialmente el recurso
    // Estados válidos: PENDIENTE, APROBADO, RECHAZADO
    @Operation(
            summary = "Cambiar estado de transacción",
            description = "Actualiza el estado de una transacción. Estados válidos: PENDIENTE, APROBADO, RECHAZADO"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "400", description = "Transacción no encontrada o estado inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionResponseDTO> cambiarEstado(
            @Parameter(description = "ID de la transacción")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado (PENDIENTE, APROBADO, RECHAZADO)")
            @RequestParam String estado) {
        return ResponseEntity.ok(transaccionService.cambiarEstado(id, estado));
    }
}