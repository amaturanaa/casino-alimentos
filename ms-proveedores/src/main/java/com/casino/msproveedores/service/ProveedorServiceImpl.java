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

@Slf4j
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
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return mapToDTO(proveedor);
    }

    @Override
    public List<ProveedorResponseDTO> listar() {
        // Alternativa clásica con ciclo FOR
        List<ProveedorResponseDTO> lista = new ArrayList<>();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        for (Proveedor p : proveedores) {
            lista.add(mapToDTO(p));
        }

        return lista;
    }

    @Override
    public List<ProveedorResponseDTO> listarActivos() {
        // Alternativa clásica con ciclo FOR
        List<ProveedorResponseDTO> lista = new ArrayList<>();
        List<Proveedor> proveedores = proveedorRepository.findByActivo(true);

        for (Proveedor p : proveedores) {
            lista.add(mapToDTO(p));
        }

        return lista;
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