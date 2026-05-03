package com.casino.mspedidos.service;

import com.casino.mspedidos.dto.DetallePedidoRequestDTO;
import com.casino.mspedidos.dto.DetallePedidoResponseDTO;
import com.casino.mspedidos.dto.PedidoRequestDTO;
import com.casino.mspedidos.dto.PedidoResponseDTO;
import com.casino.mspedidos.model.DetallePedido;
import com.casino.mspedidos.model.Pedido;
import com.casino.mspedidos.repository.DetallePedidoRepository;
import com.casino.mspedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        double total = dto.getDetalles().stream()
                .mapToDouble(DetallePedidoRequestDTO::getSubTotal)
                .sum();

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setSedeId(dto.getSedeId());
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("RECIBIDO");
        pedido.setTotalPedido(total);

        Pedido guardado = pedidoRepository.save(pedido);

        List<DetallePedido> detalles = dto.getDetalles().stream().map(d -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(guardado);
            detalle.setPlatoId(d.getPlatoId());
            detalle.setCantidad(d.getCantidad());
            detalle.setSubTotal(d.getSubTotal());
            return detalle;
        }).collect(Collectors.toList());

        detallePedidoRepository.saveAll(detalles);
        guardado.setDetalles(detalles);

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
        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> listarPorSede(Long sedeId) {
        return pedidoRepository.findBySedeId(sedeId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO cambiarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        List<String> estadosValidos = List.of(
                "RECIBIDO", "EN_PREPARACION", "LISTO_RETIRO", "ENTREGADO");
        if (!estadosValidos.contains(estado))
            throw new RuntimeException("Estado inválido");

        pedido.setEstado(estado);
        return mapToDTO(pedidoRepository.save(pedido));
    }

    @Override
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PedidoResponseDTO mapToDTO(Pedido p) {
        List<DetallePedidoResponseDTO> detalles = detallePedidoRepository
                .findByPedido_IdPedido(p.getIdPedido())
                .stream()
                .map(d -> new DetallePedidoResponseDTO(
                        d.getIdDetallePedido(), d.getPlatoId(),
                        d.getCantidad(), d.getSubTotal()))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                p.getIdPedido(), p.getUsuarioId(), p.getSedeId(),
                p.getFechaHora(), p.getEstado(), p.getTotalPedido(), detalles
        );
    }
}
