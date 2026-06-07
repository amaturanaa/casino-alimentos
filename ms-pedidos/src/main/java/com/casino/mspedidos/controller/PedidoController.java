package com.casino.mspedidos.controller;

import com.casino.mspedidos.dto.PedidoRequestDTO;
import com.casino.mspedidos.dto.PedidoResponseDTO;
import com.casino.mspedidos.service.PedidoService;
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

// Controlador REST que expone los endpoints del recurso Pedido
// Sigue el patrón CSR: solo orquesta llamadas al Service, sin lógica de negocio
// Gestiona la creación y seguimiento de pedidos del sistema de casino
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión y seguimiento de pedidos del casino de alimentos")
public class PedidoController {

    // Inyección de dependencia mediante constructor (RequiredArgsConstructor de Lombok)
    // El controller conoce solo la interfaz, no la implementación
    private final PedidoService pedidoService;

    // POST /api/pedidos — Crea un nuevo pedido con sus detalles
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    @Operation(summary = "Crear un pedido",
            description = "Registra un nuevo pedido con sus detalles en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o sede/plato no válidos")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(
            @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.crearPedido(dto));
    }

    // GET /api/pedidos — Lista todos los pedidos del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @Operation(summary = "Listar todos los pedidos",
            description = "Obtiene la lista completa de pedidos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    // GET /api/pedidos/{id} — Obtiene un pedido por su id
    // @PathVariable extrae el valor {id} desde la URL
    // Si no existe, GlobalExceptionHandler retorna 400
    @Operation(summary = "Obtener un pedido por id",
            description = "Busca y retorna un pedido según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(
            @Parameter(description = "Identificador único del pedido")
            @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    // GET /api/pedidos/usuario/{usuarioId} — Lista pedidos de un usuario específico
    // usuarioId es referencia a ms-usuarios sin FK física entre bases de datos
    @Operation(summary = "Listar pedidos por usuario",
            description = "Obtiene todos los pedidos realizados por un usuario específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos del usuario obtenida correctamente")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorUsuario(
            @Parameter(description = "Identificador del usuario (referencia a ms-usuarios)")
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(usuarioId));
    }

    // GET /api/pedidos/sede/{sedeId} — Lista pedidos de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    @Operation(summary = "Listar pedidos por sede",
            description = "Obtiene todos los pedidos asociados a una sede específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos de la sede obtenida correctamente")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorSede(
            @Parameter(description = "Identificador de la sede (referencia a ms-sucursales)")
            @PathVariable Long sedeId) {
        return ResponseEntity.ok(pedidoService.listarPorSede(sedeId));
    }

    // GET /api/pedidos/estado/{estado} — Lista pedidos filtrados por estado
    // Ejemplo: RECIBIDO, EN_PREPARACION, LISTO_RETIRO, ENTREGADO
    @Operation(summary = "Listar pedidos por estado",
            description = "Filtra los pedidos según su estado: RECIBIDO, EN_PREPARACION, LISTO_RETIRO, ENTREGADO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos filtrada correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado del pedido a filtrar: RECIBIDO, EN_PREPARACION, LISTO_RETIRO o ENTREGADO")
            @PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.listarPorEstado(estado));
    }

    // PATCH /api/pedidos/{id}/estado — Cambia el estado de un pedido
    // @RequestParam recibe el parámetro desde la URL: ?estado=EN_PREPARACION
    // Usa PATCH porque modifica parcialmente el recurso
    @Operation(summary = "Cambiar el estado de un pedido",
            description = "Actualiza el estado de un pedido según el valor enviado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del pedido actualizado"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado o estado inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> cambiarEstado(
            @Parameter(description = "Identificador único del pedido")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pedido")
            @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }
}