package com.casino.msproveedores.service;

import com.casino.msproveedores.dto.ProveedorRequestDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.model.Proveedor;
import com.casino.msproveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProveedorServiceImpl implements ProveedorService {
    private final ProveedorRepository proveedorRepository;

    @Override
    public ProveedorResponseDTO crear(ProveedorRequestDTO dto) {
        if (proveedorRepository.existsByRutProveedor(dto.getRutProveedor()))
            throw new RuntimeException("El proveedor ya existe");

        Proveedor proveedor = new Proveedor(
                null, dto.getRutProveedor(), dto.getDvRunProveedor(),
                dto.getRazonSocial(), dto.getEmailProveedor(),
                dto.getTelefono(), true
        );
        return mapToDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    @Override
    public List<ProveedorResponseDTO> listar() {
        return proveedorRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<ProveedorResponseDTO> listarActivos() {
        return proveedorRepository.findByActivo(true)
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedor.setRazonSocial(dto.getRazonSocial());
        proveedor.setEmailProveedor(dto.getEmailProveedor());
        proveedor.setTelefono(dto.getTelefono());
        return mapToDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public ProveedorResponseDTO cambiarEstado(Long id, Boolean activo) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedor.setActivo(activo);
        return mapToDTO(proveedorRepository.save(proveedor));
    }

    private ProveedorResponseDTO mapToDTO(Proveedor p) {
        return new ProveedorResponseDTO(
                p.getIdProveedor(), p.getRutProveedor(), p.getDvRunProveedor(),
                p.getRazonSocial(), p.getEmailProveedor(), p.getTelefono(), p.getActivo()
        );
    }
}


