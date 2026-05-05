package com.casino.msreservas.service;

import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import com.casino.msreservas.model.Reserva;
import com.casino.msreservas.model.TurnoDisponible;
import com.casino.msreservas.repository.ReservaRepository;
import com.casino.msreservas.repository.TurnoDisponibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final TurnoDisponibleRepository turnoRepository;

    @Override
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        TurnoDisponible turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        if (turno.getCuposRestantes() <= 0)
            throw new RuntimeException("No hay cupos disponibles en este turno");

        if (reservaRepository.existsByUsuarioIdAndTurno_IdTurnoAndEstado(
                dto.getUsuarioId(), dto.getTurnoId(), "ACTIVA"))
            throw new RuntimeException("El usuario ya tiene una reserva activa en este turno");

        turno.setCuposRestantes(turno.getCuposRestantes() - 1);
        turnoRepository.save(turno);

        Reserva reserva = new Reserva(
                null, dto.getUsuarioId(), turno,
                dto.getSedeId(), LocalDateTime.now(), "ACTIVA"
        );

        return mapToDTO(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    @Override
    public List<ReservaResponseDTO> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarPorTurno(Long turnoId) {
        return reservaRepository.findByTurno_IdTurno(turnoId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarPorSede(Long sedeId) {
        return reservaRepository.findBySedeId(sedeId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ReservaResponseDTO cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getEstado().equals("CANCELADA"))
            throw new RuntimeException("La reserva ya está cancelada");

        TurnoDisponible turno = reserva.getTurno();
        turno.setCuposRestantes(turno.getCuposRestantes() + 1);
        turnoRepository.save(turno);

        reserva.setEstado("CANCELADA");
        return mapToDTO(reservaRepository.save(reserva));
    }

    @Override
    public List<ReservaResponseDTO> listar() {
        return reservaRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReservaResponseDTO mapToDTO(Reserva r) {
        return new ReservaResponseDTO(
                r.getIdReserva(), r.getUsuarioId(),
                r.getTurno().getIdTurno(), r.getSedeId(),
                r.getFechaCreacion(), r.getEstado(),
                r.getTurno().getHoraInicio(), r.getTurno().getHoraFin()
        );
    }
}