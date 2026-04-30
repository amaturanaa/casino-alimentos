package com.casino.mssucursales.service;


import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.model.SedeCasino;
import com.casino.mssucursales.repository.SedeCasinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SedeCasinoServiceImpl implements SedeCasinoService {

    private final SedeCasinoRepository sedeCasinoRepository;

    @Override
    public SedeCasinoResponseDTO crearSede(SedeCasinoRequestDTO dto) {
        SedeCasino sede = new SedeCasino(
                null,
                dto.getNombreSede(),
                dto.getDireccion(),
                dto.getCapacidadComensales(),
                dto.getHoraApertura(),
                dto.getHoraCierre(),
                true
        );
        return mapToResponse(sedeCasinoRepository.save(sede));
    }

    @Override
    public List<SedeCasinoResponseDTO> listarSedes() {
        return sedeCasinoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SedeCasinoResponseDTO obtenerPorId(Long id) {
        return sedeCasinoRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
    }


    @Override
    public SedeCasinoResponseDTO cambiarEstado(Long id, Boolean estado) {
        SedeCasino sede = sedeCasinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        sede.setEstadoOperativo(estado);
        return mapToResponse(sedeCasinoRepository.save(sede));
    }

    private SedeCasinoResponseDTO mapToResponse(SedeCasino sede) {
        return new SedeCasinoResponseDTO(
                sede.getIdSedeCasino(),
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getCapacidadComensales(),
                sede.getHoraApertura(),
                sede.getHoraCierre(),
                sede.getEstadoOperativo()
        );
    }
}
