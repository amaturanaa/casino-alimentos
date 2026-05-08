package com.casino.msmenu.service;

import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.model.Plato;
import com.casino.msmenu.model.TipoPlato;
import com.casino.msmenu.repository.PlatoRepository;
import com.casino.msmenu.repository.TipoPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;
    private final TipoPlatoRepository tipoPlatoRepository;

    @Override
    public PlatoResponseDTO crear(PlatoRequestDTO dto) {
        TipoPlato tipoPlato = tipoPlatoRepository.findById(dto.getTipoPlatoId())
                .orElseThrow(() -> new RuntimeException("TipoPlato no encontrado"));

        Plato plato = new Plato(
                null,
                dto.getNombrePlato(),
                dto.getDescripcionPlato(),
                dto.getPrecioReferencial(),
                tipoPlato,
                dto.getCategoriaId(),
                true
        );

        return mapToDTO(platoRepository.save(plato));
    }

    @Override
    public List<PlatoResponseDTO> listar() {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findAll();

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

    @Override
    public PlatoResponseDTO obtenerPorId(Long id) {

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        return mapToDTO(plato);
    }

    @Override
    public PlatoResponseDTO cambiarDisponibilidad(Long id, Boolean disponible) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        plato.setDisponible(disponible);
        return mapToDTO(platoRepository.save(plato));
    }

    @Override
    public List<PlatoResponseDTO> listarPorTipo(Long tipoPlatoId) {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByTipoPlato_IdTipoPlato(tipoPlatoId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

    @Override
    public List<PlatoResponseDTO> listarPorCategoria(Long categoriaId) {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByCategoriaId(categoriaId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

    private PlatoResponseDTO mapToDTO(Plato p) {
        return new PlatoResponseDTO(
                p.getIdPlato(),
                p.getNombrePlato(),
                p.getDescripcionPlato(),
                p.getPrecioReferencial(),
                p.getTipoPlato().getNombreTipoPlato(),
                p.getCategoriaId(),
                p.getDisponible()
        );
    }
}
