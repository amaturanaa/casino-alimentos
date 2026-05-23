package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.model.SedeCasino;
import com.casino.mssucursales.repository.SedeCasinoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        List<SedeCasinoResponseDTO> lista = new ArrayList<>();
        List<SedeCasino> sedes = sedeCasinoRepository.findAll();

        for (SedeCasino s : sedes) {
            lista.add(mapToResponse(s));
        }
        return lista;
    }

    @Override
    public SedeCasinoResponseDTO obtenerPorId(Long id) {
        SedeCasino sede = sedeCasinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
        return mapToResponse(sede);
    }

    @Override
    public SedeCasinoResponseDTO cambiarEstado(Long id, Boolean estado) {
        SedeCasino sede = sedeCasinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
        sede.setEstadoOperativo(estado);
        return mapToResponse(sedeCasinoRepository.save(sede));
    }

    @Override
    public List<SedeCasinoResponseDTO> listarPorEstado(Boolean estado) {
        List<SedeCasinoResponseDTO> lista = new ArrayList<>();
        List<SedeCasino> sedes = sedeCasinoRepository.findByEstadoOperativo(estado);

        for (SedeCasino s : sedes) {
            lista.add(mapToResponse(s));
        }
        return lista;
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