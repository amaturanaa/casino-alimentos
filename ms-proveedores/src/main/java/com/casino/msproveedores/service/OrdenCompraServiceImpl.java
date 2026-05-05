package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.DetalleOrdenCompraRequestDTO;
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
import java.util.ArrayList;
import java.util.List;

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

        // Cálculo del costo total usando un FOR clásico en lugar de streams
        double costoTotal = 0.0;
        for (DetalleOrdenCompraRequestDTO d : dto.getDetalles()) {
            costoTotal = costoTotal + (d.getCantidad() * d.getPrecioUnitario());
        }

        OrdenCompra orden = new OrdenCompra(
                null, proveedor, dto.getSedeId(),
                LocalDateTime.now(), "PENDIENTE", costoTotal
        );

        OrdenCompra guardada = ordenCompraRepository.save(orden);

        // Creación de detalles usando un FOR clásico
        List<DetalleOrdenCompra> detalles = new ArrayList<>();
        for (DetalleOrdenCompraRequestDTO d : dto.getDetalles()) {
            double sub = d.getCantidad() * d.getPrecioUnitario();
            DetalleOrdenCompra detalle = new DetalleOrdenCompra(
                    null, guardada, d.getNombreProducto(),
                    d.getCantidad(), d.getPrecioUnitario(), sub
            );
            detalles.add(detalle);
        }

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
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findAll();

        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository.findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId) {
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findByProveedor_IdProveedor(proveedorId);

        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository.findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorSede(Long sedeId) {
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findBySedeId(sedeId);

        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository.findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorEstado(String estado) {
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findByEstado(estado);

        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository.findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public OrdenCompraResponseDTO cambiarEstado(Long id, String estado) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<String> estadosValidos = new ArrayList<>();
        estadosValidos.add("PENDIENTE");
        estadosValidos.add("RECIBIDA");
        estadosValidos.add("CANCELADA");

        if (!estadosValidos.contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }

        orden.setEstado(estado);
        ordenCompraRepository.save(orden);
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(orden, detalles);
    }

    private OrdenCompraResponseDTO mapToDTO(OrdenCompra o, List<DetalleOrdenCompra> detalles) {
        // Mapeo de detalles usando FOR clásico
        List<DetalleOrdenCompraResponseDTO> detallesDTO = new ArrayList<>();
        for (DetalleOrdenCompra d : detalles) {
            DetalleOrdenCompraResponseDTO dto = new DetalleOrdenCompraResponseDTO(
                    d.getIdDetalle(), d.getNombreProducto(),
                    d.getCantidad(), d.getPrecioUnitario(), d.getSubTotal()
            );
            detallesDTO.add(dto);
        }

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