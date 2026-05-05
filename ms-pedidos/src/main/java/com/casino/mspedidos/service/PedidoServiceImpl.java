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
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        double total = 0;
        for (DetallePedidoRequestDTO d : dto.getDetalles()) {
            total += d.getSubTotal();
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setSedeId(dto.getSedeId());
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("RECIBIDO");
        pedido.setTotalPedido(total);

        Pedido guardado = pedidoRepository.save(pedido);

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

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorSede(Long sedeId) {

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findBySedeId(sedeId);

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        return lista;
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(String estado) {

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        return lista;
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

        List<PedidoResponseDTO> lista = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findAll();

        for (Pedido pedido : pedidos) {
            lista.add(mapToDTO(pedido));
        }

        return lista;
    }

    private PedidoResponseDTO mapToDTO(Pedido p) {

        List<DetallePedidoResponseDTO> detalles = new ArrayList<>();
        List<DetallePedido> listadetalles = detallePedidoRepository.findByPedido_IdPedido(p.getIdPedido());

        for (DetallePedido d : listadetalles) {
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
