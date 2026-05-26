package com.casino.mspedidos.service;

import com.casino.mspedidos.client.MenuClient;
import com.casino.mspedidos.client.SucursalesClient;
import com.casino.mspedidos.dto.*;
import com.casino.mspedidos.model.DetallePedido;
import com.casino.mspedidos.model.Pedido;
import com.casino.mspedidos.repository.DetallePedidoRepository;
import com.casino.mspedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Pedido
// Contiene toda la lógica de negocio relacionada a pedidos del casino
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    // Repositorio JPA para acceso a datos de Pedido
    private final PedidoRepository pedidoRepository;

    private final SucursalesClient sucursalesClient;  // ← agregar
    private final MenuClient menuClient;

    // Repositorio JPA para acceso a datos de DetallePedido
    // Necesario para persistir y consultar los detalles de cada pedido
    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        log.info("Creando pedido para usuario: {} sede: {}",
                dto.getUsuarioId(), dto.getSedeId());

        // Comunicación Feign con ms-sucursales para verificar sede
        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede()
                        + " no esta operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("La sede")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede con id: "
                    + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede: " + e.getMessage());
        }

        // Comunicación Feign con ms-menu para verificar cada plato
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            try {
                PlatoResponseDTO plato = menuClient.obtenerPlatoPorId(d.getPlatoId());
                if (!plato.getDisponible()) {
                    log.warn("Plato no disponible: {}", d.getPlatoId());
                    throw new RuntimeException("El plato " + plato.getNombrePlato()
                            + " no está disponible");
                }
                log.info("Plato verificado: {}", plato.getNombrePlato());
            } catch (RuntimeException e) {
                if (e.getMessage() != null && e.getMessage().startsWith("El plato")) {
                    throw e;
                }
                log.error("Error al verificar plato: {}", e.getMessage());
                throw new RuntimeException("No se pudo verificar el plato con id: "
                        + d.getPlatoId());
            } catch (Exception e) {
                log.error("Error inesperado al verificar plato: {}", e.getMessage());
                throw new RuntimeException("No se pudo verificar el plato: " + e.getMessage());
            }
        }

        // Calcula el total del pedido sumando los subtotales
        double total = 0;
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            total += d.getSubTotal();
        }
        log.info("Total calculado para el pedido: {}", total);

        // Construcción de la entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setSedeId(dto.getSedeId());
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("RECIBIDO");
        pedido.setTotalPedido(total);

        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido guardado con id: {}", guardado.getIdPedido());

        // Construcción y persistencia de los detalles
        List<DetallePedido> detalles = new ArrayList<>();
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(guardado);
            detalle.setPlatoId(d.getPlatoId());
            detalle.setCantidad(d.getCantidad());
            detalle.setSubTotal(d.getSubTotal());
            detalles.add(detalle);
        }

        detallePedidoRepository.saveAll(detalles);
        guardado.setDetalles(detalles);
        log.info("Pedido creado con {} detalles", detalles.size());
        return mapToDTO(guardado);
    }

    @Override
    public PedidoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando pedido con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pedido no encontrado con id: {}", id);
                    return new RuntimeException("Pedido no encontrado");
                });
        return mapToDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> listarPorUsuario(Long usuarioId) {
        log.info("Listando pedidos para usuario: {}", usuarioId);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        log.info("Total pedidos para usuario {}: {}", usuarioId, lista.size());
        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando pedidos para sede: {}", sedeId);

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findBySedeId(sedeId);

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        log.info("Total pedidos para sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(String estado) {
        log.info("Listando pedidos con estado: {}", estado);

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        log.info("Total pedidos con estado {}: {}", estado, lista.size());
        return lista;
    }

    @Override
    public PedidoResponseDTO cambiarEstado(Long id, String estado) {
        log.info("Cambiando estado de pedido {} a {}", id, estado);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pedido no encontrado para cambiar estado: {}", id);
                    return new RuntimeException("Pedido no encontrado");
                });

        // Validación de negocio: solo se aceptan estados válidos del flujo del pedido
        List<String> estadosValidos = List.of(
                "RECIBIDO", "EN_PREPARACION", "LISTO_RETIRO", "ENTREGADO");
        if (!estadosValidos.contains(estado)) {
            log.warn("Estado inválido para pedido: {}", estado);
            throw new RuntimeException("Estado inválido");
        }

        // Actualiza solo el estado — no modifica otros campos del pedido
        pedido.setEstado(estado);
        Pedido actualizado = pedidoRepository.save(pedido);
        log.info("Estado de pedido {} actualizado a {}", id, estado);
        return mapToDTO(actualizado);
    }

    @Override
    public List<PedidoResponseDTO> listar() {
        log.info("Listando todos los pedidos");

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findAll();

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        log.info("Total pedidos encontrados: {}", lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Consulta los detalles del pedido via repository para incluirlos en la respuesta
    private PedidoResponseDTO mapToDTO(Pedido p) {
        List<DetallePedidoResponseDTO> detalles = new ArrayList<>();
        List<DetallePedido> listaDetalles = detallePedidoRepository
                .findByPedido_IdPedido(p.getIdPedido());

        // Bucle for tradicional para convertir lista de detalles a lista de DTOs
        for (DetallePedido d : listaDetalles) {
            DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO(
                    d.getIdDetallePedido(),
                    d.getPlatoId(),
                    d.getCantidad(),
                    d.getSubTotal()
            );
            detalles.add(dto);
        }

        return new PedidoResponseDTO(
                p.getIdPedido(),
                p.getUsuarioId(),
                p.getSedeId(),
                p.getFechaHora(),
                p.getEstado(),
                p.getTotalPedido(),
                detalles
        );
    }
}