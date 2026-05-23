package com.casino.msproveedores.service;

import com.casino.msproveedores.client.SucursalesClient;
import com.casino.msproveedores.dto.DetalleOrdenCompraRequestDTO;
import com.casino.msproveedores.dto.DetalleOrdenCompraResponseDTO;
import com.casino.msproveedores.dto.OrdenCompraRequestDTO;
import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.dto.SedeCasinoResponseDTO;
import com.casino.msproveedores.model.DetalleOrdenCompra;
import com.casino.msproveedores.model.OrdenCompra;
import com.casino.msproveedores.model.Proveedor;
import com.casino.msproveedores.repository.DetalleOrdenCompraRepository;
import com.casino.msproveedores.repository.OrdenCompraRepository;
import com.casino.msproveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {


    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleRepository;
    private final ProveedorRepository proveedorRepository;
    private final SucursalesClient sucursalesClient;

    @Override
    public OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto) {
        log.info("Creando orden de compra para proveedor: {} sede: {}",
                dto.getProveedorId(), dto.getSedeId());

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado: {}", dto.getProveedorId());
                    return new RuntimeException("Proveedor no encontrado");
                });

        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa para orden de compra: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede()
                        + " no está operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede con id: "
                    + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede: " + e.getMessage());
        }

        double costoTotal = 0;
        for (DetalleOrdenCompraRequestDTO d : dto.getDetalles()) {
            costoTotal = costoTotal + (d.getCantidad() * d.getPrecioUnitario());
        }

        OrdenCompra orden = new OrdenCompra(
                null, proveedor, dto.getSedeId(),
                LocalDateTime.now(), "PENDIENTE", costoTotal
        );

        OrdenCompra guardada = ordenCompraRepository.save(orden);

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

        log.info("Orden de compra creada con id: {}", guardada.getIdOrdenCompra());
        return mapToDTO(guardada, detalles);
    }

    @Override
    public OrdenCompraResponseDTO obtenerPorId(Long id) {
        log.info("Buscando orden de compra con id: {}", id);
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Orden no encontrada con id: {}", id);
                    return new RuntimeException("Orden no encontrada");
                });
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(orden, detalles);
    }

    @Override
    public List<OrdenCompraResponseDTO> listar() {
        log.info("Listando todas las órdenes de compra");
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findAll();
        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository
                    .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        log.info("Total órdenes encontradas: {}", lista.size());
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId) {
        log.info("Listando órdenes para proveedor: {}", proveedorId);
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository
                .findByProveedor_IdProveedor(proveedorId);
        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository
                    .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando órdenes para sede: {}", sedeId);
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findBySedeId(sedeId);
        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository
                    .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorEstado(String estado) {
        log.info("Listando órdenes con estado: {}", estado);
        List<OrdenCompraResponseDTO> lista = new ArrayList<>();
        List<OrdenCompra> ordenes = ordenCompraRepository.findByEstado(estado);
        for (OrdenCompra o : ordenes) {
            List<DetalleOrdenCompra> detalles = detalleRepository
                    .findByOrdenCompra_IdOrdenCompra(o.getIdOrdenCompra());
            lista.add(mapToDTO(o, detalles));
        }
        return lista;
    }

    @Override
    public OrdenCompraResponseDTO cambiarEstado(Long id, String estado) {
        log.info("Cambiando estado de orden {} a {}", id, estado);
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Orden no encontrada para cambiar estado: {}", id);
                    return new RuntimeException("Orden no encontrada");
                });

        List<String> estadosValidos = new ArrayList<>();
        estadosValidos.add("PENDIENTE");
        estadosValidos.add("RECIBIDA");
        estadosValidos.add("CANCELADA");

        if (!estadosValidos.contains(estado)) {
            log.warn("Estado inválido: {}", estado);
            throw new RuntimeException("Estado inválido");
        }

        orden.setEstado(estado);
        ordenCompraRepository.save(orden);
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        log.info("Estado de orden {} actualizado a {}", id, estado);
        return mapToDTO(orden, detalles);
    }

    private OrdenCompraResponseDTO mapToDTO(OrdenCompra o,
                                            List<DetalleOrdenCompra> detalles) {
        List<DetalleOrdenCompraResponseDTO> detallesDTO = new ArrayList<>();
        for (DetalleOrdenCompra d : detalles) {
            DetalleOrdenCompraResponseDTO detalleDTO = new DetalleOrdenCompraResponseDTO(
                    d.getIdDetalle(), d.getNombreProducto(),
                    d.getCantidad(), d.getPrecioUnitario(), d.getSubTotal()
            );
            detallesDTO.add(detalleDTO);
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