package com.casino.mspagos.service;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.model.Transaccion;
import com.casino.mspagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;

    @Override
    public TransaccionResponseDTO procesarPago(TransaccionRequestDTO dto) {
        if (transaccionRepository.findByPedidoId(dto.getPedidoId()).isPresent())
            throw new RuntimeException("Ya existe un pago para este pedido");

        List<String> metodosValidos = List.of(
                "TARJETA_CREDITO", "JUNAEB", "SUBSIDIO_EMPRESA", "EFECTIVO");
        if (!metodosValidos.contains(dto.getMetodoPago()))
            throw new RuntimeException("Método de pago inválido");

        Transaccion transaccion = new Transaccion();
        transaccion.setPedidoId(dto.getPedidoId());
        transaccion.setUsuarioId(dto.getUsuarioId());
        transaccion.setMonto(dto.getMonto());
        transaccion.setMetodoPago(dto.getMetodoPago());
        transaccion.setFechaPago(LocalDateTime.now());
        transaccion.setEstadoPago("PENDIENTE");

        return mapToDTO(transaccionRepository.save(transaccion));
    }

    @Override
    public TransaccionResponseDTO obtenerPorId(Long id) {
        return transaccionRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Override
    public TransaccionResponseDTO obtenerPorPedido(Long pedidoId) {
        return transaccionRepository.findByPedidoId(pedidoId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Override
    public List<TransaccionResponseDTO> listarPorUsuario(Long usuarioId) {
        return transaccionRepository.findByUsuarioId(usuarioId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransaccionResponseDTO> listarPorEstado(String estado) {
        return transaccionRepository.findByEstadoPago(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransaccionResponseDTO> listarPorMetodo(String metodoPago) {
        return transaccionRepository.findByMetodoPago(metodoPago)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TransaccionResponseDTO cambiarEstado(Long id, String estado) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        List<String> estadosValidos = List.of("PENDIENTE", "APROBADO", "RECHAZADO");
        if (!estadosValidos.contains(estado))
            throw new RuntimeException("Estado inválido");

        transaccion.setEstadoPago(estado);
        return mapToDTO(transaccionRepository.save(transaccion));
    }

    @Override
    public List<TransaccionResponseDTO> listar() {
        return transaccionRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TransaccionResponseDTO mapToDTO(Transaccion t) {
        return new TransaccionResponseDTO(
                t.getIdTransaccion(), t.getPedidoId(), t.getUsuarioId(),
                t.getMonto(), t.getMetodoPago(), t.getFechaPago(), t.getEstadoPago()
        );
    }
}
