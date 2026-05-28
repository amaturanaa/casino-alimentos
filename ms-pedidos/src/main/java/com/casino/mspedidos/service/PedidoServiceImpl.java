package com.casino.mspedidos.service;

import com.casino.mspedidos.client.MenuClient;
import com.casino.mspedidos.client.SucursalesClient;
import com.casino.mspedidos.dto.DetallePedidoRequestDTO;
import com.casino.mspedidos.dto.DetallePedidoResponseDTO;
import com.casino.mspedidos.dto.PedidoRequestDTO;
import com.casino.mspedidos.dto.PedidoResponseDTO;
import com.casino.mspedidos.dto.PlatoResponseDTO;
import com.casino.mspedidos.dto.SedeCasinoResponseDTO;
import com.casino.mspedidos.model.DetallePedido;
import com.casino.mspedidos.model.Pedido;
import com.casino.mspedidos.repository.DetallePedidoRepository;
import com.casino.mspedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import feign.FeignException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    // Inyección de los Feign Clients para la comunicación remota
    private final SucursalesClient sucursalesClient;
    private final MenuClient menuClient;

    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {

        // =================================================================
        // 1. VALIDACIÓN REMOTA: Verificar Sede mediante ms-sucursales
        // =================================================================
        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());

            if (sede == null) {
                throw new RuntimeException("La sede especificada no existe en el sistema.");
            }

            if (!sede.getEstadoOperativo()) {
                throw new RuntimeException("Operación rechazada: La sede '" + sede.getNombreSede() + "' no se encuentra operativa.");
            }

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Error: La sede con ID " + dto.getSedeId() + " no fue encontrada en ms-sucursales.");
        } catch (RuntimeException re) {
            throw re; // Relanzamos nuestros errores explícitos de negocio
        } catch (Exception e) {
            throw new RuntimeException("Error temporal de comunicación con el servicio de sucursales.");
        }

        // =================================================================
        // 2. VALIDACIÓN REMOTA: Verificar Disponibilidad de Platos mediante ms-menu
        // =================================================================
        for (DetallePedidoRequestDTO detalleDto : dto.getDetalles()) {
            try {
                PlatoResponseDTO plato = menuClient.obtenerPlatoPorId(detalleDto.getPlatoId());

                if (plato == null) {
                    throw new RuntimeException("El plato con ID " + detalleDto.getPlatoId() + " no existe.");
                }

                if (!plato.getDisponible()) {
                    throw new RuntimeException("El plato '" + plato.getNombrePlato() + "' no está disponible en el menú actual.");
                }

            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Error: El plato con ID " + detalleDto.getPlatoId() + " no existe en el catálogo general.");
            } catch (RuntimeException re) {
                throw re; // Relanzamos nuestro error explícito de disponibilidad
            } catch (Exception e) {
                throw new RuntimeException("Error temporal de comunicación con el servicio de menú.");
            }
        }

        // =================================================================
        // 3. LÓGICA DE NEGOCIO Y PERSISTENCIA (Ciclos FOR Tradicionales)
        // =================================================================
        double total = 0;
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            total += d.getSubTotal();
        }

        // Crear la entidad principal Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setSedeId(dto.getSedeId());
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("RECIBIDO");
        pedido.setTotalPedido(total);

        // Guardar el pedido para generar su ID
        Pedido guardado = pedidoRepository.save(pedido);

        // Crear y guardar la lista de detalles
        List<DetallePedido> detallesGuardados = new ArrayList<>();
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(guardado);
            detalle.setPlatoId(d.getPlatoId());
            detalle.setCantidad(d.getCantidad());
            detalle.setSubTotal(d.getSubTotal());
            detallesGuardados.add(detallePedidoRepository.save(detalle));
        }

        // Asignar los detalles guardados a la entidad principal
        guardado.setDetalles(detallesGuardados);

        return mapToDTO(guardado);
    }

    @Override
    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return mapToDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> listarPorUsuario(Long usuarioId) {
        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        for (Pedido p : pedidos) {
            lista.add(mapToDTO(p));
        }
        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorSede(Long sedeId) {
        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findBySedeId(sedeId);
        for (Pedido p : pedidos) {
            lista.add(mapToDTO(p));
        }
        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(String estado) {
        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        for (Pedido p : pedidos) {
            lista.add(mapToDTO(p));
        }
        return lista;
    }

    @Override
    @Transactional
    public PedidoResponseDTO cambiarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        List<String> estadosValidos = List.of(
                "RECIBIDO", "EN_PREPARACION", "LISTO_RETIRO", "ENTREGADO");
        if (!estadosValidos.contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }

        pedido.setEstado(estado);
        return mapToDTO(pedidoRepository.save(pedido));
    }

    @Override
    public List<PedidoResponseDTO> listar() {
        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findAll();
        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }
        return lista;
    }

    // Método de mapeo manual (Sin Streams) para cumplir con el estándar
    private PedidoResponseDTO mapToDTO(Pedido p) {
        List<DetallePedidoResponseDTO> detallesDto = new ArrayList<>();
        List<DetallePedido> listaDetalles = detallePedidoRepository.findByPedido_IdPedido(p.getIdPedido());

        for (DetallePedido d : listaDetalles) {
            DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO(
                    d.getIdDetallePedido(),
                    d.getPlatoId(),
                    d.getCantidad(),
                    d.getSubTotal()
            );
            detallesDto.add(dto);
        }

        return new PedidoResponseDTO(
                p.getIdPedido(),
                p.getUsuarioId(),
                p.getSedeId(),
                p.getFechaHora(),
                p.getEstado(),
                p.getTotalPedido(),
                detallesDto
        );
    }
}