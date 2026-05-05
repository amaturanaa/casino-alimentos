package com.casino.msempleados.service;

import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import com.casino.msempleados.model.Empleado;
import com.casino.msempleados.model.TurnoEmpleado;
import com.casino.msempleados.repository.EmpleadoRepository;
import com.casino.msempleados.repository.TurnoEmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoEmpleadoServiceImpl implements TurnoEmpleadoService {

    private final TurnoEmpleadoRepository turnoRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    public TurnoEmpleadoResponseDTO crear(TurnoEmpleadoRequestDTO dto) {
        Empleado empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        TurnoEmpleado turno = new TurnoEmpleado();
        turno.setEmpleado(empleado);
        turno.setSedeId(dto.getSedeId());
        turno.setFecha(dto.getFecha());
        turno.setHoraEntrada(dto.getHoraEntrada());
        turno.setHoraSalida(dto.getHoraSalida());
        turno.setTipoTurno(dto.getTipoTurno());

        return mapToDTO(turnoRepository.save(turno));
    }

    @Override
    public TurnoEmpleadoResponseDTO obtenerPorId(Long id) {
        TurnoEmpleado turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        return mapToDTO(turno);
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listar() {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findAll();
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorEmpleado(Long idEmpleado) {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findByEmpleado_IdEmpleado(idEmpleado);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorSede(Long sedeId) {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findBySedeId(sedeId);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorFecha(LocalDate fecha) {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findByFecha(fecha);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorSedeYFecha(Long sedeId, LocalDate fecha) {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findBySedeIdAndFecha(sedeId, fecha);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorTipo(String tipoTurno) {
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        // Corrección: se quitó la palabra "String" dentro del paréntesis
        List<TurnoEmpleado> turnos = turnoRepository.findByTipoTurno(tipoTurno);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public void eliminar(Long id) {
        TurnoEmpleado turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        turnoRepository.delete(turno);
    }

    private TurnoEmpleadoResponseDTO mapToDTO(TurnoEmpleado t) {
        String nombre = t.getEmpleado().getPnombreEmpleado()
                + " " + t.getEmpleado().getAppaternoEmpleado();

        return new TurnoEmpleadoResponseDTO(
                t.getIdTurno(),
                t.getEmpleado().getIdEmpleado(),
                nombre,
                t.getSedeId(),
                t.getFecha(),
                t.getHoraEntrada(),
                t.getHoraSalida(),
                t.getTipoTurno()
        );
    }
}