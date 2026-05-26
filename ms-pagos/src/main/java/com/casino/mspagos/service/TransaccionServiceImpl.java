package com.casino.mspagos.service;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.model.Transaccion;
import com.casino.mspagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Transaccion
// Contiene toda la lógica de negocio relacionada a pagos del sistema
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    // Repositorio JPA inyectado mediante constructor
    private final TransaccionRepository transaccionRepository;

    @Override
    public TransaccionResponseDTO procesarPago(TransaccionRequestDTO dto) {
        log.info("Procesando pago para pedido: {} usuario: {}",
                dto.getPedidoId(), dto.getUsuarioId());

        // Validación de negocio: un pedido solo puede tener un pago (relación 1 a 1)
        if (transaccionRepository.findByPedidoId(dto.getPedidoId()).isPresent()) {
            log.warn("Ya existe un pago para pedido: {}", dto.getPedidoId());
            throw new RuntimeException("Ya existe un pago para este pedido");
        }

        // Validación de negocio: solo se aceptan métodos de pago válidos del sistema
        List<String> metodosValidos = List.of(
                "TARJETA_CREDITO", "JUNAEB", "SUBSIDIO_EMPRESA", "EFECTIVO");
        if (!metodosValidos.contains(dto.getMetodoPago())) {
            log.warn("Método de pago inválido: {}", dto.getMetodoPago());
            throw new RuntimeException("Método de pago inválido");
        }

        // Construcción de la entidad Transaccion desde el DTO de entrada
        // Estado inicial siempre es PENDIENTE al crear el pago
        // LocalDateTime.now() registra la fecha y hora exacta del pago
        Transaccion transaccion = new Transaccion();
        transaccion.setPedidoId(dto.getPedidoId());
        transaccion.setUsuarioId(dto.getUsuarioId());
        transaccion.setMonto(dto.getMonto());
        transaccion.setMetodoPago(dto.getMetodoPago());
        transaccion.setFechaPago(LocalDateTime.now());
        transaccion.setEstadoPago("PENDIENTE");

        // Persistencia en base de datos mediante JpaRepository
        Transaccion guardada = transaccionRepository.save(transaccion);
        log.info("Pago procesado con id: {}", guardada.getIdTransaccion());
        return mapToDTO(guardada);
    }

    @Override
    public TransaccionResponseDTO obtenerPorId(Long id) {
        log.info("Buscando transacción con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transacción no encontrada con id: {}", id);
                    return new RuntimeException("Transacción no encontrada");
                });

        return mapToDTO(transaccion);
    }

    @Override
    public TransaccionResponseDTO obtenerPorPedido(Long pedidoId) {
        log.info("Buscando transacción para pedido: {}", pedidoId);

        Transaccion transaccion = transaccionRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> {
                    log.error("Transacción no encontrada para pedido: {}", pedidoId);
                    return new RuntimeException("Transacción no encontrada");
                });

        return mapToDTO(transaccion);
    }

    @Override
    public List<TransaccionResponseDTO> listarPorUsuario(Long usuarioId) {
        log.info("Listando transacciones para usuario: {}", usuarioId);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findByUsuarioId(usuarioId);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        log.info("Total transacciones para usuario {}: {}", usuarioId, lista.size());
        return lista;
    }

    @Override
    public List<TransaccionResponseDTO> listarPorEstado(String estado) {
        log.info("Listando transacciones con estado: {}", estado);

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findByEstadoPago(estado);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        log.info("Total transacciones con estado {}: {}", estado, lista.size());
        return lista;
    }

    @Override
    public List<TransaccionResponseDTO> listarPorMetodo(String metodoPago) {
        log.info("Listando transacciones por método de pago: {}", metodoPago);

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        // Corrección: se usa findByMetodoPago en vez de findByEstadoPago
        List<Transaccion> transacciones = transaccionRepository.findByMetodoPago(metodoPago);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        log.info("Total transacciones con método {}: {}", metodoPago, lista.size());
        return lista;
    }

    @Override
    public TransaccionResponseDTO cambiarEstado(Long id, String estado) {
        log.info("Cambiando estado de transacción {} a {}", id, estado);

        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transacción no encontrada para cambiar estado: {}", id);
                    return new RuntimeException("Transacción no encontrada");
                });

        // Validación de negocio: solo se aceptan estados válidos del sistema
        List<String> estadosValidos = List.of("PENDIENTE", "APROBADO", "RECHAZADO");
        if (!estadosValidos.contains(estado)) {
            log.warn("Estado inválido para transacción: {}", estado);
            throw new RuntimeException("Estado inválido");
        }

        // Actualiza solo el estado — no modifica otros campos de la transacción
        transaccion.setEstadoPago(estado);
        Transaccion actualizada = transaccionRepository.save(transaccion);
        log.info("Estado de transacción {} actualizado a {}", id, estado);
        return mapToDTO(actualizada);
    }

    @Override
    public List<TransaccionResponseDTO> listar() {
        log.info("Listando todas las transacciones");

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findAll();

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        log.info("Total transacciones encontradas: {}", lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    private TransaccionResponseDTO mapToDTO(Transaccion t) {
        return new TransaccionResponseDTO(
                t.getIdTransaccion(),
                t.getPedidoId(),
                t.getUsuarioId(),
                t.getMonto(),
                t.getMetodoPago(),
                t.getFechaPago(),
                t.getEstadoPago()
        );
    }
}