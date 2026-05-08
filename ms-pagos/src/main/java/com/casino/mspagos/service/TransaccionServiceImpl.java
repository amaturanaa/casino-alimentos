package com.casino.mspagos.service;

import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.model.Transaccion;
import com.casino.mspagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        return mapToDTO(transaccion);
    }

    @Override
    public TransaccionResponseDTO obtenerPorPedido(Long pedidoId) {

        Transaccion transaccion = transaccionRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        return mapToDTO(transaccion);
    }

    @Override
    public List<TransaccionResponseDTO> listarPorUsuario(Long usuarioId) {

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findByUsuarioId(usuarioId);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        return lista;
    }

    @Override
    public List<TransaccionResponseDTO> listarPorEstado(String estado) {

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findByEstadoPago(estado);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        return lista;
    }

    @Override
    public List<TransaccionResponseDTO> listarPorMetodo(String metodoPago) {

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findByEstadoPago(metodoPago);

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        return lista;
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

        List<TransaccionResponseDTO> lista = new ArrayList<>();
        List<Transaccion> transacciones = transaccionRepository.findAll();

        for (Transaccion trans : transacciones) {
            lista.add(mapToDTO(trans));
        }

        return lista;
    }

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
