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

// Implementación del Service para la entidad OrdenCompra
// Contiene toda la lógica de negocio relacionada a órdenes de compra
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {

    // Repositorio JPA para acceso a datos de OrdenCompra
    private final OrdenCompraRepository ordenCompraRepository;

    // Repositorio JPA para acceso a datos de DetalleOrdenCompra
    // Necesario para persistir y consultar los detalles de cada orden
    private final DetalleOrdenCompraRepository detalleRepository;

    // Repositorio JPA para acceso a datos de Proveedor
    // Necesario para verificar que el proveedor exista antes de crear la orden
    private final ProveedorRepository proveedorRepository;

    // Cliente Feign para comunicación síncrona con ms-sucursales
    // Verifica que la sede exista y esté operativa antes de crear la orden
    private final SucursalesClient sucursalesClient;

    @Override
    public OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto) {
        log.info("Creando orden de compra para proveedor: {} sede: {}",
                dto.getProveedorId(), dto.getSedeId());

        // Verifica que el proveedor exista en la base de datos local
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado: {}", dto.getProveedorId());
                    return new RuntimeException("Proveedor no encontrado");
                });

        // Comunicación Feign con ms-sucursales para verificar sede
        // try/catch diferenciado: RuntimeException relanza limpio, Exception captura errores de red
        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa para orden de compra: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede()
                        + " no está operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            // Si el mensaje empieza con "La sede" es un error de negocio — se relanza sin modificar
            if (e.getMessage() != null && e.getMessage().startsWith("La sede")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede con id: "
                    + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede: " + e.getMessage());
        }

        // Calcula el costo total sumando cantidad * precioUnitario de cada detalle
        // Bucle for tradicional sin usar streams ni lambdas
        double costoTotal = 0;
        for (DetalleOrdenCompraRequestDTO d : dto.getDetalles()) {
            costoTotal = costoTotal + (d.getCantidad() * d.getPrecioUnitario());
        }
        log.info("Costo total calculado: {}", costoTotal);

        // Construcción de la entidad OrdenCompra desde el DTO de entrada
        // Estado inicial siempre es PENDIENTE al crear la orden
        // LocalDateTime.now() registra la fecha y hora exacta de la solicitud
        OrdenCompra orden = new OrdenCompra(
                null, proveedor, dto.getSedeId(),
                LocalDateTime.now(), "PENDIENTE", costoTotal
        );

        // Persiste la orden primero para obtener el id generado
        // Los detalles necesitan el id de la orden para la FK
        OrdenCompra guardada = ordenCompraRepository.save(orden);
        log.info("Orden guardada con id: {}", guardada.getIdOrdenCompra());

        // Construcción de los detalles usando bucle for tradicional
        List<DetalleOrdenCompra> detalles = new ArrayList<>();
        for (DetalleOrdenCompraRequestDTO d : dto.getDetalles()) {
            double sub = d.getCantidad() * d.getPrecioUnitario();
            DetalleOrdenCompra detalle = new DetalleOrdenCompra(
                    null, guardada, d.getNombreProducto(),
                    d.getCantidad(), d.getPrecioUnitario(), sub
            );
            detalles.add(detalle);
        }

        // Persiste todos los detalles en una sola operación
        detalleRepository.saveAll(detalles);
        log.info("Orden de compra creada con id: {}", guardada.getIdOrdenCompra());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardada, detalles);
    }

    @Override
    public OrdenCompraResponseDTO obtenerPorId(Long id) {
        log.info("Buscando orden de compra con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
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

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
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
        log.info("Total órdenes para proveedor {}: {}", proveedorId, lista.size());
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
        log.info("Total órdenes para sede {}: {}", sedeId, lista.size());
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
        log.info("Total órdenes con estado {}: {}", estado, lista.size());
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

        // Validación de negocio: solo se aceptan estados válidos del sistema
        List<String> estadosValidos = new ArrayList<>();
        estadosValidos.add("PENDIENTE");
        estadosValidos.add("RECIBIDA");
        estadosValidos.add("CANCELADA");

        if (!estadosValidos.contains(estado)) {
            log.warn("Estado inválido para orden: {}", estado);
            throw new RuntimeException("Estado inválido");
        }

        // Actualiza solo el estado — no modifica otros campos de la orden
        orden.setEstado(estado);
        ordenCompraRepository.save(orden);
        List<DetalleOrdenCompra> detalles = detalleRepository
                .findByOrdenCompra_IdOrdenCompra(id);
        log.info("Estado de orden {} actualizado a {}", id, estado);
        return mapToDTO(orden, detalles);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye datos del proveedor y lista de detalles para respuesta completa
    private OrdenCompraResponseDTO mapToDTO(OrdenCompra o,
                                            List<DetalleOrdenCompra> detalles) {
        // Bucle for tradicional para convertir lista de detalles a lista de DTOs
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