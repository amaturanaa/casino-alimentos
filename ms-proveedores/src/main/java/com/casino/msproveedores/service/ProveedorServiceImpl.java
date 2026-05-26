package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.model.Proveedor;
import com.casino.msproveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Proveedor
// Contiene toda la lógica de negocio relacionada a proveedores
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    // Repositorio JPA inyectado mediante constructor
    private final ProveedorRepository proveedorRepository;

    @Override
    public ProveedorResponseDTO crear(ProveedorRequestDTO dto) {
        log.info("Creando proveedor con RUT: {}", dto.getRutProveedor());

        // Validación de negocio: RUT único en el sistema
        if (proveedorRepository.existsByRutProveedor(dto.getRutProveedor())) {
            log.warn("Proveedor ya existe con RUT: {}", dto.getRutProveedor());
            throw new RuntimeException("El proveedor ya existe");
        }

        // Construcción de la entidad desde el DTO de entrada
        // true como activo porque nuevo proveedor siempre activo
        Proveedor proveedor = new Proveedor(
                null, dto.getRutProveedor(), dto.getDvRunProveedor(),
                dto.getRazonSocial(), dto.getEmailProveedor(),
                dto.getTelefono(), true
        );

        // Persistencia en base de datos mediante JpaRepository
        Proveedor guardado = proveedorRepository.save(proveedor);
        log.info("Proveedor creado con id: {}", guardado.getIdProveedor());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardado);
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Long id) {
        log.info("Buscando proveedor con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado con id: {}", id);
                    return new RuntimeException("Proveedor no encontrado");
                });
        return mapToDTO(proveedor);
    }

    @Override
    public List<ProveedorResponseDTO> listar() {
        log.info("Listando todos los proveedores");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<ProveedorResponseDTO> lista = new ArrayList<>();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        for (Proveedor p : proveedores) {
            lista.add(mapToDTO(p));
        }

        log.info("Total proveedores encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public List<ProveedorResponseDTO> listarActivos() {
        log.info("Listando proveedores activos");

        List<ProveedorResponseDTO> lista = new ArrayList<>();
        List<Proveedor> proveedores = proveedorRepository.findByActivo(true);

        for (Proveedor p : proveedores) {
            lista.add(mapToDTO(p));
        }

        log.info("Total proveedores activos: {}", lista.size());
        return lista;
    }

    @Override
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        log.info("Actualizando proveedor con id: {}", id);

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado para actualizar: {}", id);
                    return new RuntimeException("Proveedor no encontrado");
                });

        // Solo permite modificar datos de contacto — el RUT no se puede cambiar
        proveedor.setRazonSocial(dto.getRazonSocial());
        proveedor.setEmailProveedor(dto.getEmailProveedor());
        proveedor.setTelefono(dto.getTelefono());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        log.info("Proveedor actualizado con id: {}", actualizado.getIdProveedor());
        return mapToDTO(actualizado);
    }

    @Override
    public ProveedorResponseDTO cambiarEstado(Long id, Boolean activo) {
        log.info("Cambiando estado de proveedor {} a {}", id, activo);

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado para cambiar estado: {}", id);
                    return new RuntimeException("Proveedor no encontrado");
                });

        // Baja lógica — no elimina el registro, solo cambia el estado
        proveedor.setActivo(activo);
        Proveedor actualizado = proveedorRepository.save(proveedor);
        log.info("Estado de proveedor {} actualizado a {}", id, activo);
        return mapToDTO(actualizado);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    private ProveedorResponseDTO mapToDTO(Proveedor p) {
        return new ProveedorResponseDTO(
                p.getIdProveedor(), p.getRutProveedor(), p.getDvRunProveedor(),
                p.getRazonSocial(), p.getEmailProveedor(), p.getTelefono(), p.getActivo()
        );
    }
}