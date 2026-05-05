package com.casino.msreservas.service;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.model.TurnoDisponible;
import com.casino.msreservas.repository.TurnoDisponibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        return turnoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public TurnoDisponibleResponseDTO obtenerPorId(Long id) {
        return turnoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorSede(Long sedeId) {
        return turnoRepository.findBySedeId(sedeId).stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorFecha(LocalDate fecha) {
        return turnoRepository.findByFecha(fecha).stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarDisponiblesPorSedeYFecha(Long sedeId, LocalDate fecha) {
        return turnoRepository
                .findBySedeIdAndFechaAndCuposRestantesGreaterThan(sedeId, fecha, 0)
                .stream().map(this::mapToDTO).toList();
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