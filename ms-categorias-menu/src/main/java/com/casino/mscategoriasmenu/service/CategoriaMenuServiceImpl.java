package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.model.CategoriaMenu;
import com.casino.mscategoriasmenu.repository.CategoriaMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaMenuServiceImpl implements CategoriaMenuService {

    private final CategoriaMenuRepository repository;

    @Override
    public CategoriaMenuResponseDTO crear(CategoriaMenuRequestDTO request) {

        if (repository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("La categoría ya existe");
        }

        CategoriaMenu categoria = new CategoriaMenu(
                null,
                request.getNombre(),
                request.getEstado()
        );

        CategoriaMenu guardada = repository.save(categoria);

        return new CategoriaMenuResponseDTO(
                guardada.getId(),
                guardada.getNombre(),
                guardada.getEstado()
        );
    }

    @Override
    public List<CategoriaMenuResponseDTO> listar() {

        List<CategoriaMenuResponseDTO> lista = new ArrayList<>();
        List<CategoriaMenu> categorias = repository.findAll();

        for (CategoriaMenu categoria : categorias) {
            CategoriaMenuResponseDTO dto = new CategoriaMenuResponseDTO(
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getEstado()
            );

            lista.add(dto);
        }

        return lista;
    }

    @Override
    public CategoriaMenuResponseDTO obtenerPorId(Long id) {
        CategoriaMenu c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return new CategoriaMenuResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getEstado());
    }

    @Override
    public CategoriaMenuResponseDTO cambiarEstado(Long id, Boolean estado) {
        CategoriaMenu c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        c.setEstado(estado);
        repository.save(c);

        return new CategoriaMenuResponseDTO(
                c.getId(), c.getNombre(), c.getEstado());
    }

    @Override
    public List<CategoriaMenuResponseDTO> listarPorEstado(Boolean estado) {

        List<CategoriaMenuResponseDTO> lista = new ArrayList<>();
        List<CategoriaMenu> categorias = repository.findByEstado(estado);

        for (CategoriaMenu categoria : categorias) {
            CategoriaMenuResponseDTO dto = new CategoriaMenuResponseDTO(
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getEstado()
            );

            lista.add(dto);
        }

        return lista;

    }
}
