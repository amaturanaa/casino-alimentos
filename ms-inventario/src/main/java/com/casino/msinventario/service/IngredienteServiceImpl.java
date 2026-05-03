package com.casino.msinventario.service;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.model.Ingrediente;
import com.casino.msinventario.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    @Override
    public IngredienteResponseDTO crear(IngredienteRequestDTO dto) {
        Ingrediente ingrediente = new Ingrediente(
                null, dto.getNombreIngrediente(), dto.getSedeId(),
                dto.getUnidadMedida(), dto.getStockActual(), dto.getStockMinimo()
        );
        return mapToDTO(ingredienteRepository.save(ingrediente));
    }

    @Override
    public IngredienteResponseDTO obtenerPorId(Long id) {
        return ingredienteRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
    }

    @Override
    public List<IngredienteResponseDTO> listar() {
        return ingredienteRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<IngredienteResponseDTO> listarPorSede(Long sedeId) {
        return ingredienteRepository.findBySedeId(sedeId)
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajo() {
        return ingredienteRepository.findAll().stream()
                .filter(i -> i.getStockActual() <= i.getStockMinimo())
                .map(this::mapToDTO).toList();
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajoPorSede(Long sedeId) {
        return ingredienteRepository.findBySedeId(sedeId).stream()
                .filter(i -> i.getStockActual() <= i.getStockMinimo())
                .map(this::mapToDTO).toList();
    }

    @Override
    public IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        ingrediente.setNombreIngrediente(dto.getNombreIngrediente());
        ingrediente.setSedeId(dto.getSedeId());
        ingrediente.setUnidadMedida(dto.getUnidadMedida());
        ingrediente.setStockActual(dto.getStockActual());
        ingrediente.setStockMinimo(dto.getStockMinimo());
        return mapToDTO(ingredienteRepository.save(ingrediente));
    }

    private IngredienteResponseDTO mapToDTO(Ingrediente i) {
        return new IngredienteResponseDTO(
                i.getIdIngrediente(), i.getNombreIngrediente(), i.getSedeId(),
                i.getUnidadMedida(), i.getStockActual(), i.getStockMinimo(),
                i.getStockActual() <= i.getStockMinimo()
        );
    }
}
