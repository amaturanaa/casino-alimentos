package com.casino.msreservas.service;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.model.TurnoDisponible;
import com.casino.msreservas.repository.TurnoDisponibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoDisponibleServiceImpl implements TurnoDisponibleService {

    private final TurnoDisponibleRepository turnoRepository;

    @Override
    public TurnoDisponibleResponseDTO crear(TurnoDisponibleRequestDTO dto) {
        TurnoDisponible turno = new TurnoDisponible(
                null, dto.getSedeId(), dto.getFecha(),
                dto.getHoraInicio(), dto.getHoraFin(),
                dto.getCapacidad(), dto.getCapacidad()
        );
        return mapToDTO(turnoRepository.save(turno));
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listar() {
        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findAll();

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public TurnoDisponibleResponseDTO obtenerPorId(Long id) {
        TurnoDisponible turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        return mapToDTO(turno);
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorSede(Long sedeId) {
        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findBySedeId(sedeId);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorFecha(LocalDate fecha) {
        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findByFecha(fecha);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarDisponiblesPorSedeYFecha(Long sedeId, LocalDate fecha) {
        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository
                .findBySedeIdAndFechaAndCuposRestantesGreaterThan(sedeId, fecha, 0);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    private TurnoDisponibleResponseDTO mapToDTO(TurnoDisponible t) {
        return new TurnoDisponibleResponseDTO(
                t.getIdTurno(), t.getSedeId(), t.getFecha(),
                t.getHoraInicio(), t.getHoraFin(),
                t.getCapacidad(), t.getCuposRestantes(),
                t.getCuposRestantes() > 0
        );
    }
}