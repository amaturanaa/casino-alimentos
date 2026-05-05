package com.casino.msinventario.service;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import com.casino.msinventario.model.TipoMovimiento;
import com.casino.msinventario.repository.TipoMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoMovimientoServiceImpl implements TipoMovimientoService{

    private final TipoMovimientoRepository tipoMovimientoRepository;

    @Override
    public TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto) {
        if (tipoMovimientoRepository.existsByNombreTipoMovimiento(dto.getNombreTipoMovimiento()))
            throw new RuntimeException("El tipo de movimiento ya existe");

        TipoMovimiento tipo = new TipoMovimiento(null, dto.getNombreTipoMovimiento());
        TipoMovimiento guardado = tipoMovimientoRepository.save(tipo);
        return new TipoMovimientoResponseDTO(guardado.getIdTipoMovimiento(), guardado.getNombreTipoMovimiento());
    }

    @Override
    public List<TipoMovimientoResponseDTO> listar() {

        List<TipoMovimientoResponseDTO>  lista = new ArrayList<>();
        List<TipoMovimiento> tipos = tipoMovimientoRepository.findAll();

        for (TipoMovimiento tipo : tipos) {
            lista.add(new TipoMovimientoResponseDTO(
                    tipo.getIdTipoMovimiento(),
                    tipo.getNombreTipoMovimiento()
            ));
        }

        return lista;
    }

    @Override
    public TipoMovimientoResponseDTO obtenerPorId(Long id) {
        TipoMovimiento t = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoMovimiento no encontrado"));
        return new TipoMovimientoResponseDTO(t.getIdTipoMovimiento(), t.getNombreTipoMovimiento());
    }


}
