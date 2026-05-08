package com.casino.msmenu.service;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.model.TipoPlato;
import com.casino.msmenu.repository.TipoPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPlatoServiceImpl implements TipoPlatoService {

    private final TipoPlatoRepository tipoPlatoRepository;

    @Override
    public TipoPlatoResponseDTO crear(TipoPlatoRequestDTO dto) {
        if (tipoPlatoRepository.existsByNombreTipoPlato(dto.getNombreTipoPlato()))
            throw new RuntimeException("El tipo de plato ya existe");

        TipoPlato tipoPlato = new TipoPlato(
                null,
                dto.getNombreTipoPlato());

        TipoPlato guardado = tipoPlatoRepository.save(tipoPlato);

        return new TipoPlatoResponseDTO(guardado.getIdTipoPlato(), guardado.getNombreTipoPlato());
    }

    @Override
    public List<TipoPlatoResponseDTO> listar() {

        List<TipoPlatoResponseDTO> lista = new ArrayList<>();
        List<TipoPlato> tipoPlatos = tipoPlatoRepository.findAll();

        for (TipoPlato tipoPlato : tipoPlatos) {
            lista.add(new TipoPlatoResponseDTO(
                    tipoPlato.getIdTipoPlato(),
                    tipoPlato.getNombreTipoPlato()
            ));
        }

        return lista;
    }

    @Override
    public TipoPlatoResponseDTO obtenerPorId(Long id) {
        TipoPlato t = tipoPlatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoPlato no encontrado"));

        return new TipoPlatoResponseDTO(
                t.getIdTipoPlato(),
                t.getNombreTipoPlato());
    }


}
