package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.DetalleOrdenCompraResponseDTO;
import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.model.DetalleOrdenCompra;
import com.casino.msproveedores.model.OrdenCompra;
import com.casino.msproveedores.model.Proveedor;
import com.casino.msproveedores.repository.DetalleOrdenCompraRepository;
import com.casino.msproveedores.repository.OrdenCompraRepository;
import com.casino.msproveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    public OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        double costoTotal = dto.getDetalles().stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();

        OrdenCompra orden = new OrdenCompra(
                null, proveedor, dto.getSedeId(),
                LocalDateTime.now(), "PENDIENTE", costoTotal
        );

        OrdenCompra guardada = ordenCompraRepository.save(orden);

        List<DetalleOrdenCompra> detalles = dto.getDetalles().stream().map(d -> {
            double sub = d.getCantidad() * d.getPrecioUnitario();
            return new DetalleOrdenCompra(
                    null, guardada, d.getNombreProducto(),
                    d.getCantidad(), d.getPrecioUnitario(), sub
            );
        }).collect(Collectors.toList());

        detalleRepository.saveAll(detalles);
        return mapToDTO(guardada, detalles);
    }

    @Override
    public OrdenCompraResponseDTO obtenerPorId(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(orden, detalles);
    }

    @Override
    public List<OrdenCompraResponseDTO> listar() {
        return ordenCompraRepository.findAll().stream()
                .map(o -> mapToDTO(o, detalleRepository
                        .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra())))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId) {
        return ordenCompraRepository.findByProveedor_IdProveedor(proveedorId).stream()
                .map(o -> mapToDTO(o, detalleRepository
                        .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra())))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorSede(Long sedeId) {
        return ordenCompraRepository.findBySedeId(sedeId).stream()
                .map(o -> mapToDTO(o, detalleRepository
                        .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra())))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorEstado(String estado) {
        return ordenCompraRepository.findByEstado(estado).stream()
                .map(o -> mapToDTO(o, detalleRepository
                        .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra())))
                .collect(Collectors.toList());
    }

    @Override
    public OrdenCompraResponseDTO cambiarEstado(Long id, String estado) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<String> estadosValidos = List.of("PENDIENTE", "RECIBIDA", "CANCELADA");
        if (!estadosValidos.contains(estado))
            throw new RuntimeException("Estado inválido");

        orden.setEstado(estado);
        ordenCompraRepository.save(orden);
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(orden, detalles);
    }

    private OrdenCompraResponseDTO mapToDTO(OrdenCompra o, List<DetalleOrdenCompra> detalles) {
        List<DetalleOrdenCompraResponseDTO> detallesDTO = detalles.stream()
                .map(d -> new DetalleOrdenCompraResponseDTO(
                        d.getIdDetalle(), d.getNombreProducto(),
                        d.getCantidad(), d.getPrecioUnitario(), d.getSubTotal()))
                .collect(Collectors.toList());

        return new OrdenCompraResponseDTO(
                o.getIdOrdenCompra(),
                o.getProveedor().getIdProveedor(),
                o.getProveedor().getRazonSocial(),
                o.getSedeId(),
                o.getFechaSolicitud(),
                o.getEstado(),
                o.getCostoTotal(),
                detallesDTO
        );
    }
}