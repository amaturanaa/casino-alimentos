package com.casino.msmenu.service;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.model.TipoPlato;
import com.casino.msmenu.repository.TipoPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPlatoServiceImpl implements TipoPlatoService {

    private final TipoPlatoRepository tipoPlatoRepository;

    @Override
    public TipoPlatoResponseDTO crear(TipoPlatoRequestDTO dto) {
        if (tipoPlatoRepository.existsByNombreTipoPlato(dto.getNombreTipoPlato()))
            throw new RuntimeException("El tipo de plato ya existe");

        TipoPlato tipoPlato = new TipoPlato(null, dto.getNombreTipoPlato());
        TipoPlato guardado = tipoPlatoRepository.save(tipoPlato);
        return new TipoPlatoResponseDTO(guardado.getIdTipoPlato(), guardado.getNombreTipoPlato());
    }

    @Override
    public List<TipoPlatoResponseDTO> listar() {
        return tipoPlatoRepository.findAll()
                .stream()
                .map(t -> new TipoPlatoResponseDTO(t.getIdTipoPlato(), t.getNombreTipoPlato()))
                .toList();
    }

    @Override
    public TipoPlatoResponseDTO obtenerPorId(Long id) {
        TipoPlato t = tipoPlatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoPlato no encontrado"));
        return new TipoPlatoResponseDTO(t.getIdTipoPlato(), t.getNombreTipoPlato());
    }


}
