package com.casino.mspedidos.service;

import com.casino.mspedidos.dto.PedidoRequestDTO;
import com.casino.mspedidos.dto.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {

    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);

    PedidoResponseDTO obtenerPorId(Long id);

    List<PedidoResponseDTO> listarPorUsuario(Long usuarioId);

    List<PedidoResponseDTO> listarPorSede(Long sedeId);

    List<PedidoResponseDTO> listarPorEstado(String estado);

    PedidoResponseDTO cambiarEstado(Long id, String estado);

    List<PedidoResponseDTO> listar();
}
