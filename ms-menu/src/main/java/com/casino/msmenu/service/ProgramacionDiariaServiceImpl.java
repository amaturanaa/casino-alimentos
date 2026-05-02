package com.casino.msmenu.service;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.model.Plato;
import com.casino.msmenu.model.ProgramacionDiaria;
import com.casino.msmenu.repository.PlatoRepository;
import com.casino.msmenu.repository.ProgramacionDiariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramacionDiariaServiceImpl implements ProgramacionDiariaService {

    private final ProgramacionDiariaRepository programacionRepository;
    private final PlatoRepository platoRepository;

    @Override
    public ProgramacionDiariaResponseDTO crear(ProgramacionDiariaRequestDTO dto) {
        Plato plato = platoRepository.findById(dto.getPlatoId())
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        ProgramacionDiaria prog = new ProgramacionDiaria(
                null, dto.getFecha(), dto.getSedeId(), plato, dto.getRacionesDisponibles()
        );

        return mapToDTO(programacionRepository.save(prog));
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorFecha(LocalDate fecha) {
        return programacionRepository.findByFecha(fecha)
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorSede(Long sedeId) {
        return programacionRepository.findBySedeId(sedeId)
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorFechaYSede(LocalDate fecha, Long sedeId) {
        return programacionRepository.findByFechaAndSedeId(fecha, sedeId)
                .stream().map(this::mapToDTO).toList();
    }

    @Override
    public ProgramacionDiariaResponseDTO descontarRacion(Long id) {
        ProgramacionDiaria prog = programacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programación no encontrada"));

        if (prog.getRacionesDisponibles() <= 0)
            throw new RuntimeException("No hay raciones disponibles");

        prog.setRacionesDisponibles(prog.getRacionesDisponibles() - 1);
        return mapToDTO(programacionRepository.save(prog));
    }

    private ProgramacionDiariaResponseDTO mapToDTO(ProgramacionDiaria p) {
        return new ProgramacionDiariaResponseDTO(
                p.getIdProgramacion(), p.getFecha(), p.getSedeId(),
                p.getPlato().getIdPlato(), p.getPlato().getNombrePlato(),
                p.getRacionesDisponibles()
        );
    }
}
