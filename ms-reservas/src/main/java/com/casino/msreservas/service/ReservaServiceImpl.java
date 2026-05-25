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

// Implementación del Service para la entidad Reserva
// Contiene toda la lógica de negocio relacionada a reservas del casino
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    // Repositorio JPA para acceso a datos de Reserva
    private final ReservaRepository reservaRepository;

    // Repositorio JPA para acceso a datos de TurnoDisponible
    // Necesario para verificar cupos y actualizar el turno al crear/cancelar reservas
    private final TurnoDisponibleRepository turnoRepository;

    // Cliente Feign para comunicación síncrona con ms-usuarios
    // Verifica que el usuario exista y esté activo antes de crear la reserva
    private final UsuariosClient usuariosClient;

    // Cliente Feign para comunicación síncrona con ms-sucursales
    // Verifica que la sede exista y esté operativa antes de crear la reserva
    private final SucursalesClient sucursalesClient;

    @Override
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        log.info("Creando reserva para usuario: {} en sede: {}",
                dto.getUsuarioId(), dto.getSedeId());

        // Comunicación Feign con ms-usuarios para verificar usuario
        // try/catch diferenciado: RuntimeException relanza limpio, Exception captura errores de red
        try {
            UsuarioResponseDTO usuario = usuariosClient.obtenerUsuarioPorId(dto.getUsuarioId());
            if (!usuario.getActivo()) {
                log.warn("Usuario inactivo: {}", dto.getUsuarioId());
                throw new RuntimeException("El usuario no esta activo en el sistema");
            }
            log.info("Usuario verificado: {}", usuario.getEmail());
        } catch (RuntimeException e) {
            // Si el mensaje empieza con "El usuario" es un error de negocio — se relanza sin modificar
            if (e.getMessage() != null && e.getMessage().startsWith("El usuario")) {
                throw e;
            }
            log.error("Error al verificar usuario: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar el usuario con id: "
                    + dto.getUsuarioId());
        } catch (Exception ex) {
            log.error("Error inesperado al verificar usuario: {}", ex.getMessage());
            throw new RuntimeException("Error al verificar usuario: " + ex.getMessage());
        }

        // Comunicación Feign con ms-sucursales para verificar sede
        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede()
                        + " no esta operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            // Si el mensaje empieza con "La sede" es un error de negocio — se relanza sin modificar
            if (e.getMessage() != null && e.getMessage().startsWith("La sede ")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("Error al verificar la sede con id: " + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("Error al verificar la sede: " + e.getMessage());
        }

        // Verifica que el turno exista en la base de datos local
        TurnoDisponible turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> {
                    log.error("Turno no encontrado: {}", dto.getTurnoId());
                    return new RuntimeException("Turno no encontrado");
                });

        // Validación de negocio: no se puede reservar si no hay cupos disponibles
        if (turno.getCuposRestantes() <= 0) {
            log.warn("Sin cupos en turno: {}", dto.getTurnoId());
            throw new RuntimeException("No hay cupos disponibles en este turno");
        }

        // Validación de negocio: un usuario no puede tener dos reservas activas en el mismo turno
        if (reservaRepository.existsByUsuarioIdAndTurno_IdTurnoAndEstado(
                dto.getUsuarioId(), dto.getTurnoId(), "ACTIVA")) {
            log.warn("Usuario {} ya tiene reserva activa en turno {}",
                    dto.getUsuarioId(), dto.getTurnoId());
            throw new RuntimeException("El usuario ya tiene una reserva activa en este turno");
        }

        // Descuenta un cupo del turno al confirmar la reserva
        turno.setCuposRestantes(turno.getCuposRestantes() - 1);
        turnoRepository.save(turno);

        // Construcción de la entidad Reserva desde el DTO de entrada
        // LocalDateTime.now() registra la fecha y hora exacta de la reserva
        Reserva reserva = new Reserva(
                null, dto.getUsuarioId(), turno,
                dto.getSedeId(), LocalDateTime.now(), "ACTIVA"
        );

        // Persistencia en base de datos mediante JpaRepository
        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva creada con id: {}", guardada.getIdReserva());
        return mapToDTO(guardada);
    }

    @Override
    public ReservaResponseDTO obtenerPorId(Long id) {
        log.info("Buscando reserva con id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva no encontrada con id: {}", id);
                    return new RuntimeException("Reserva no encontrada");
                });
        return mapToDTO(reserva);
    }

    @Override
    public List<ReservaResponseDTO> listarPorUsuario(Long usuarioId) {
        log.info("Listando reservas para usuario: {}", usuarioId);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);
        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        log.info("Total reservas para usuario {}: {}", usuarioId, lista.size());
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorTurno(Long turnoId) {
        log.info("Listando reservas para turno: {}", turnoId);
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByTurno_IdTurno(turnoId);
        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        log.info("Total reservas para turno {}: {}", turnoId, lista.size());
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando reservas para sede: {}", sedeId);
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findBySedeId(sedeId);
        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        log.info("Total reservas para sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public List<ReservaResponseDTO> listarPorEstado(String estado) {
        log.info("Listando reservas con estado: {}", estado);
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByEstado(estado);
        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        log.info("Total reservas con estado {}: {}", estado, lista.size());
        return lista;
    }

    @Override
    public ReservaResponseDTO cancelar(Long id) {
        log.info("Cancelando reserva con id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva no encontrada para cancelar: {}", id);
                    return new RuntimeException("Reserva no encontrada");
                });

        // Validación de negocio: no se puede cancelar una reserva ya cancelada
        if (reserva.getEstado().equals("CANCELADA")) {
            log.warn("Reserva {} ya está cancelada", id);
            throw new RuntimeException("La reserva ya está cancelada");
        }

        // Devuelve el cupo al turno al cancelar la reserva
        TurnoDisponible turno = reserva.getTurno();
        turno.setCuposRestantes(turno.getCuposRestantes() + 1);
        turnoRepository.save(turno);
        log.info("Cupo devuelto al turno: {}", turno.getIdTurno());

        // Actualiza el estado de la reserva a CANCELADA — baja lógica
        reserva.setEstado("CANCELADA");
        Reserva cancelada = reservaRepository.save(reserva);
        log.info("Reserva {} cancelada exitosamente", id);
        return mapToDTO(cancelada);
    }

    @Override
    public List<ReservaResponseDTO> listar() {
        log.info("Listando todas las reservas");
        List<ReservaResponseDTO> lista = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findAll();
        for (Reserva r : reservas) {
            lista.add(mapToDTO(r));
        }
        log.info("Total reservas encontradas: {}", lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye datos del turno para enriquecer la respuesta
    private ReservaResponseDTO mapToDTO(Reserva r) {
        return new ReservaResponseDTO(
                r.getIdReserva(), r.getUsuarioId(),
                r.getTurno().getIdTurno(), r.getSedeId(),
                r.getFechaCreacion(), r.getEstado(),
                r.getTurno().getHoraInicio(), r.getTurno().getHoraFin()
        );
    }
}