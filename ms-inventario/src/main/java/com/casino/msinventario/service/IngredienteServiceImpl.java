package com.casino.msinventario.service;

import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.model.Ingrediente;
import com.casino.msinventario.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    @Override
    public IngredienteResponseDTO crear(IngredienteRequestDTO dto) {
        Ingrediente ingrediente = new Ingrediente(
                null,
                dto.getNombreIngrediente(),
                dto.getSedeId(),
                dto.getUnidadMedida(),
                dto.getStockActual(),
                dto.getStockMinimo()
        );
        return mapToDTO(ingredienteRepository.save(ingrediente));
    }

    @Override
    public IngredienteResponseDTO obtenerPorId(Long id) {

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        return mapToDTO(ingrediente);
    }

    @Override
    public List<IngredienteResponseDTO> listar() {

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        for (Ingrediente ingrediente : ingredientes) {
            lista.add(mapToDTO(ingrediente));
        }

        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarPorSede(Long sedeId) {

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findBySedeId(sedeId);

        for (Ingrediente ingrediente : ingredientes) {
            lista.add(mapToDTO(ingrediente));
        }

        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajo() {

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.getStockActual() <= ingrediente.getStockMinimo()) {
                lista.add(mapToDTO(ingrediente));
            }
        }

        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajoPorSede(Long sedeId) {

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findBySedeId(sedeId);

        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.getStockActual() <= ingrediente.getStockMinimo()) {
                lista.add(mapToDTO(ingrediente));
            }
        }

        return lista;
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
                i.getIdIngrediente(),
                i.getNombreIngrediente(),
                i.getSedeId(),
                i.getUnidadMedida(),
                i.getStockActual(),
                i.getStockMinimo(),
                i.getStockActual() <= i.getStockMinimo()
        );
    }
}
