package com.casino.msproveedores.service;


import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;

import java.util.List;

public interface OrdenCompraService {
    OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto);
    OrdenCompraResponseDTO obtenerPorId(Long id);
    List<OrdenCompraResponseDTO> listar();
    List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId);
    List<OrdenCompraResponseDTO> listarPorSede(Long sedeId);
    List<OrdenCompraResponseDTO> listarPorEstado(String estado);
    OrdenCompraResponseDTO cambiarEstado(Long id, String estado);
}
