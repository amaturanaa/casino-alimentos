package com.casino.msreservas.service;

import com.casino.msreservas.client.SucursalesClient;
import com.casino.msreservas.client.UsuariosClient;
import com.casino.msreservas.dto.ReservaRequestDTO;
import com.casino.msreservas.dto.ReservaResponseDTO;
import com.casino.msreservas.dto.SedeCasinoResponseDTO;
import com.casino.msreservas.dto.UsuarioResponseDTO;
import com.casino.msreservas.model.Reserva;
import com.casino.msreservas.model.TurnoDisponible;
import com.casino.msreservas.repository.ReservaRepository;
import com.casino.msreservas.repository.TurnoDisponibleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final TurnoDisponibleRepository turnoRepository;
    private final UsuariosClient usuariosClient;
    private final SucursalesClient sucursalesClient;


    @Override
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        log.info("Creando reserva para usuario: {} en sede: {}", dto.getUsuarioId(), dto.getSedeId());

        try {
            UsuarioResponseDTO usuario = usuariosClient.obtenerUsuarioPorId(dto.getUsuarioId());
            if (!usuario.getActivo()) {
                log.warn("Usuario inactivo: {}", dto.getUsuarioId());
                throw new RuntimeException("El usuario no esta activo en el sistema");
            }
            log.error("Error al verificar usuario: {}", usuario.getEmail());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("El usuario")) {
                throw e;
            }
            log.error("Error al verificar usuario: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar el usuario con id: " + dto.getUsuarioId());
        } catch (Exception ex) {
            log.error("Error inesperado al verificar usuario: {}", ex.getMessage());
            throw new RuntimeException("Error al verificar usuario: " + ex.getMessage());
        }

        try{
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede() + " no esta operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("La sede ")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("Error al verificar la sede con id: " + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("Error al verificar la sede: " +  e.getMessage());
        }


        TurnoDisponible turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> {
                    log.error("Turno no encontrado: {}", dto.getTurnoId());
                    return new RuntimeException("Turno no encontrado");
                });

        if (turno.getCuposRestantes() <= 0) {
            log.warn("Sin cupos en turno: {}", dto.getTurnoId());
            throw new RuntimeException("No hay cupos disponibles en este turno");
        }

        if (reservaRepository.existsByUsuarioIdAndTurno_IdTurnoAndEstado(
                dto.getUsuarioId(), dto.getTurnoId(), "ACTIVA")) {
            log.warn("Usuario {} ya tiene reserva activa en turno {}", dto.getUsuarioId(), dto.getTurnoId());
            throw new RuntimeException("El usuario ya tiene una reserva activa en este turno");
        }

        turno.setCuposRestantes(turno.getCuposRestantes() - 1);
        turnoRepository.save(turno);

        Reserva reserva = new Reserva(
                null, dto.getUsuarioId(), turno,
                dto.getSedeId(), LocalDateTime.now(), "ACTIVA"
        );


        Reserva guardada =  reservaRepository.save(reserva);
        log.info("Reserva creada con id: {}", guardada.getIdReserva());
        return mapToDTO(guardada);
    }

    @Override
    public ReservaResponseDTO obtenerPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return mapToDTO(reserva);
    }

    @Override
    public List<ReservaResponseDTO> listarPorUsuario(Long usuarioId) {
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);

        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorTurno(Long turnoId) {
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByTurno_IdTurno(turnoId);

        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorSede(Long sedeId) {
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findBySedeId(sedeId);

        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorEstado(String estado) {
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByEstado(estado);

        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        return lista;
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
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findAll();

        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        return lista;
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