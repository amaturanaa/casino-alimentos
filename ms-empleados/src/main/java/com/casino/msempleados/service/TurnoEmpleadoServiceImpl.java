package com.casino.msempleados.service;

import com.casino.msempleados.client.SucursalesClient;
import com.casino.msempleados.dto.SedeCasinoResponseDTO;
import com.casino.msempleados.dto.TurnoEmpleadoRequestDTO;
import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import com.casino.msempleados.model.Empleado;
import com.casino.msempleados.model.TurnoEmpleado;
import com.casino.msempleados.repository.EmpleadoRepository;
import com.casino.msempleados.repository.TurnoEmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TurnoEmpleadoServiceImpl implements TurnoEmpleadoService {

    private final TurnoEmpleadoRepository turnoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final SucursalesClient sucursalesClient;


    @Override
    public TurnoEmpleadoResponseDTO crear(TurnoEmpleadoRequestDTO dto) {
        log.info("Creando turno para empleado: {} en sede: {}",
                dto.getIdEmpleado(), dto.getSedeId());

        Empleado empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado: {}", dto.getIdEmpleado());
                    return new RuntimeException("Empleado no encontrado");
                });

        if(!empleado.getActivo()) {
            log.warn("Empleado inactivo: {}", dto.getIdEmpleado());
            throw new RuntimeException("El empleado no está activo");
        }

        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtennerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa para turno: {}", dto.getSedeId());
                throw new RuntimeException("La sede "+ sede.getNombreSede()+" no está operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        }catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("La sede")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede con id: "+ dto.getSedeId());
        }catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar sede: "+ e.getMessage());

        }

        TurnoEmpleado turno = new TurnoEmpleado();
        turno.setEmpleado(empleado);
        turno.setSedeId(dto.getSedeId());
        turno.setFecha(dto.getFecha());
        turno.setHoraEntrada(dto.getHoraEntrada());
        turno.setHoraSalida(dto.getHoraSalida());
        turno.setTipoTurno(dto.getTipoTurno());

        TurnoEmpleado guardado = turnoRepository.save(turno);
        log.info("Turno creado con id: {}", guardado.toString());
        return mapToDTO(guardado);
    }

    @Override
    public TurnoEmpleadoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando turno con id: {}", id);
        TurnoEmpleado turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        return mapToDTO(turno);
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listar() {
        log.info("Listando todos los turnos");
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findAll();
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorEmpleado(Long idEmpleado) {
        log.info("Listando turnos para empleados: {}", idEmpleado);
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findByEmpleado_IdEmpleado(idEmpleado);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando turnos para sede: {}", sedeId);
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findBySedeId(sedeId);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorFecha(LocalDate fecha) {
        log.info("Listando turnos para fecha: {}", fecha);
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findByFecha(fecha);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorSedeYFecha(Long sedeId, LocalDate fecha) {
        log.info("Listando turnos para sede: {} fecha: {}", sedeId, fecha);
        List<TurnoEmpleadoResponseDTO> lista = new ArrayList<>();
        List<TurnoEmpleado> turnos = turnoRepository.findBySedeIdAndFecha(sedeId, fecha);
        for (TurnoEmpleado t : turnos) {
            lista.add(mapToDTO(t));
        }
        return lista;
    }

    @Override
    public List<TurnoEmpleadoResponseDTO> listarPorTipo(String tipoTurno) {
        log.info("Listando turnos por tipo: {}", tipoTurno);
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
        log.info("Eliminando turno con id: {}", id);
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