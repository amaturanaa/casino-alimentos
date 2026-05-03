package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.model.CategoriaMenu;
import com.casino.mscategoriasmenu.model.EtiquetaNutricional;
import com.casino.mscategoriasmenu.repository.CategoriaMenuRepository;
import com.casino.mscategoriasmenu.repository.EtiquetaNutricionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtiquetaNutricionalServiceImpl implements EtiquetaNutricionalService {

    private final EtiquetaNutricionalRepository etiquetaRepository;
    private final CategoriaMenuRepository categoriaRepository;

    @Override
    public EtiquetaNutricionalResponseDTO crear(EtiquetaNutricionalRequestDTO dto) {

        if (etiquetaRepository.existsByCategoriaMenu_Id(dto.getCategoriaId())) {
            throw new RuntimeException("Ya existe etiqueta para esta categoría");
        }

        CategoriaMenu categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        EtiquetaNutricional etiqueta = new EtiquetaNutricional();
        etiqueta.setCategoriaMenu(categoria);
        etiqueta.setCalorias(dto.getCalorias());
        etiqueta.setProteinas(dto.getProteinas());
        etiqueta.setCarbohidratos(dto.getCarbohidratos());
        etiqueta.setGrasas(dto.getGrasas());
        etiqueta.setEsVegetariano(dto.getEsVegetariano());
        etiqueta.setEsVegano(dto.getEsVegano());
        etiqueta.setContieneGluten(dto.getContieneGluten());

        return mapToDTO(etiquetaRepository.save(etiqueta));
    }

    @Override
    public EtiquetaNutricionalResponseDTO obtenerPorCategoria(Long categoriaId) {
        return etiquetaRepository.findByCategoriaMenu_Id(categoriaId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
    }

    @Override
    public EtiquetaNutricionalResponseDTO actualizar(Long id, EtiquetaNutricionalRequestDTO dto) {

        EtiquetaNutricional etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));

        etiqueta.setCalorias(dto.getCalorias());
        etiqueta.setProteinas(dto.getProteinas());
        etiqueta.setCarbohidratos(dto.getCarbohidratos());
        etiqueta.setGrasas(dto.getGrasas());
        etiqueta.setEsVegetariano(dto.getEsVegetariano());
        etiqueta.setEsVegano(dto.getEsVegano());
        etiqueta.setContieneGluten(dto.getContieneGluten());

        return mapToDTO(etiquetaRepository.save(etiqueta));
    }

    @Override
    public List<EtiquetaNutricionalResponseDTO> listar() {
        return etiquetaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private EtiquetaNutricionalResponseDTO mapToDTO(EtiquetaNutricional e) {
        return new EtiquetaNutricionalResponseDTO(
                e.getIdEtiquetaNutricional(),
                e.getCategoriaMenu().getId(),
                e.getCategoriaMenu().getNombre(),
                e.getCalorias(),
                e.getProteinas(),
                e.getCarbohidratos(),
                e.getGrasas(),
                e.getEsVegetariano(),
                e.getEsVegano(),
                e.getContieneGluten()
        );
    }
}