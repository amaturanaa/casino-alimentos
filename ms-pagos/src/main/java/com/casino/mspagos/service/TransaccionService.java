package com.casino.mspagos.service;


import com.casino.mspagos.dto.TransaccionRequestDTO;
import com.casino.mspagos.dto.TransaccionResponseDTO;

import java.util.List;

public interface TransaccionService {


    TransaccionResponseDTO procesarPago(TransaccionRequestDTO dto);

    TransaccionResponseDTO obtenerPorId(Long id);

    TransaccionResponseDTO obtenerPorPedido(Long pedidoId);

    List<TransaccionResponseDTO> listarPorUsuario(Long usuarioId);

    List<TransaccionResponseDTO> listarPorEstado(String estado);

    List<TransaccionResponseDTO> listarPorMetodo(String metodoPago);

    TransaccionResponseDTO cambiarEstado(Long id, String estado);

    List<TransaccionResponseDTO> listar();
}
