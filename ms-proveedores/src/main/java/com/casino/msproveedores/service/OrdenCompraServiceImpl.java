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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    @Transactional
    public OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto) {
        // 1. Validar que el proveedor exista
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // 2. Calcular el costo total sumando (cantidad * precio) de todos los detalles
        double costoTotal = dto.getDetalles().stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();

        // 3. Crear y guardar la Orden de Compra principal
        OrdenCompra ordenCompra = new OrdenCompra(
                null,
                proveedor,
                dto.getSedeId(),
                LocalDateTime.now(),
                "PENDIENTE", // Estado inicial por defecto
                costoTotal
        );
        OrdenCompra ordenGuardada = ordenCompraRepository.save(ordenCompra);

        // 4. Crear y guardar los detalles de la orden (productos solicitados para el casino)
        List<DetalleOrdenCompra> detallesGuardados = dto.getDetalles().stream().map(d -> {
            Double subTotal = d.getCantidad() * d.getPrecioUnitario();
            DetalleOrdenCompra detalle = new DetalleOrdenCompra(
                    null,
                    ordenGuardada,
                    d.getNombreProducto(),
                    d.getCantidad(),
                    d.getPrecioUnitario(),
                    subTotal
            );
            return detalleOrdenCompraRepository.save(detalle);
        }).toList();

        // 5. Retornar el DTO mapeado
        return mapToDTO(ordenGuardada, detallesGuardados);
    }

    @Override
    public OrdenCompraResponseDTO obtenerPorId(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));
        List<DetalleOrdenCompra> detalles = detalleOrdenCompraRepository.findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(orden, detalles);
    }

    @Override
    public List<OrdenCompraResponseDTO> listar() {
        return ordenCompraRepository.findAll().stream()
                .map(this::mapOrdenWithDetalles)
                .toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorProveedor(Long proveedorId) {
        return ordenCompraRepository.findByProveedor_IdProveedor(proveedorId).stream()
                .map(this::mapOrdenWithDetalles)
                .toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorSede(Long sedeId) {
        return ordenCompraRepository.findBySedeId(sedeId).stream()
                .map(this::mapOrdenWithDetalles)
                .toList();
    }

    @Override
    public List<OrdenCompraResponseDTO> listarPorEstado(String estado) {
        return ordenCompraRepository.findByEstado(estado).stream()
                .map(this::mapOrdenWithDetalles)
                .toList();
    }

    @Override
    @Transactional
    public OrdenCompraResponseDTO cambiarEstado(Long id, String estado) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        orden.setEstado(estado); // Ej: cambiar a "APROBADA", "ENTREGADA" o "CANCELADA"
        OrdenCompra ordenActualizada = ordenCompraRepository.save(orden);

        List<DetalleOrdenCompra> detalles = detalleOrdenCompraRepository.findByOrdenCompra_IdOrdenCompra(id);
        return mapToDTO(ordenActualizada, detalles);
    }

    // --- MÉTODOS DE MAPEO (Privados) ---

    private OrdenCompraResponseDTO mapOrdenWithDetalles(OrdenCompra orden) {
        List<DetalleOrdenCompra> detalles = detalleOrdenCompraRepository.findByOrdenCompra_IdOrdenCompra(orden.getIdOrdenCompra());
        return mapToDTO(orden, detalles);
    }

    private OrdenCompraResponseDTO mapToDTO(OrdenCompra orden, List<DetalleOrdenCompra> detalles) {
        List<DetalleOrdenCompraResponseDTO> detallesDTO = detalles.stream()
                .map(d -> new DetalleOrdenCompraResponseDTO(
                        d.getIdDetalle(),
                        d.getNombreProducto(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubTotal()
                )).toList();

        return new OrdenCompraResponseDTO(
                orden.getIdOrdenCompra(),
                orden.getProveedor().getIdProveedor(),
                orden.getProveedor().getRazonSocial(), // Permite ver el nombre rápido en el front
                orden.getSedeId(),
                orden.getFechaSolicitud(),
                orden.getEstado(),
                orden.getCostoTotal(),
                detallesDTO
        );
    }
}
