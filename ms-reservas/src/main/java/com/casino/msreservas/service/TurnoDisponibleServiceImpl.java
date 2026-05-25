package com.casino.msreservas.service;

import com.casino.msreservas.dto.TurnoDisponibleRequestDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.model.TurnoDisponible;
import com.casino.msreservas.repository.TurnoDisponibleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad TurnoDisponible
// Contiene toda la lógica de negocio relacionada a turnos disponibles
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class TurnoDisponibleServiceImpl implements TurnoDisponibleService {

    // Repositorio JPA inyectado mediante constructor
    private final TurnoDisponibleRepository turnoRepository;

    @Override
    public TurnoDisponibleResponseDTO crear(TurnoDisponibleRequestDTO dto) {
        log.info("Creando turno para sede: {} fecha: {}", dto.getSedeId(), dto.getFecha());

        // Los cuposRestantes se inicializan igual a la capacidad total al crear el turno
        // Se irán decrementando conforme los usuarios realicen reservas
        TurnoDisponible turno = new TurnoDisponible(
                null, dto.getSedeId(), dto.getFecha(),
                dto.getHoraInicio(), dto.getHoraFin(),
                dto.getCapacidad(), dto.getCapacidad()
        );

        // Persistencia en base de datos mediante JpaRepository
        TurnoDisponible guardado = turnoRepository.save(turno);
        log.info("Turno creado con id: {}", guardado.getIdTurno());
        return mapToDTO(guardado);
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listar() {
        log.info("Listando todos los turnos disponibles");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findAll();

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }

        log.info("Total turnos encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public TurnoDisponibleResponseDTO obtenerPorId(Long id) {
        log.info("Buscando turno con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        TurnoDisponible turno = turnoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Turno no encontrado con id: {}", id);
                    return new RuntimeException("Turno no encontrado");
                });
        return mapToDTO(turno);
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando turnos para sede: {}", sedeId);

        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findBySedeId(sedeId);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }

        log.info("Total turnos para sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarPorFecha(LocalDate fecha) {
        log.info("Listando turnos para fecha: {}", fecha);

        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();
        List<TurnoDisponible> turnos = turnoRepository.findByFecha(fecha);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }

        log.info("Total turnos para fecha {}: {}", fecha, lista.size());
        return lista;
    }

    @Override
    public List<TurnoDisponibleResponseDTO> listarDisponiblesPorSedeYFecha(
            Long sedeId, LocalDate fecha) {
        log.info("Listando turnos disponibles para sede: {} fecha: {}", sedeId, fecha);

        List<TurnoDisponibleResponseDTO> lista = new ArrayList<>();

        // Filtra turnos con cuposRestantes > 0 usando Derived Query del repositorio
        List<TurnoDisponible> turnos = turnoRepository
                .findBySedeIdAndFechaAndCuposRestantesGreaterThan(sedeId, fecha, 0);

        for (TurnoDisponible t : turnos) {
            lista.add(mapToDTO(t));
        }

        log.info("Total turnos disponibles para sede {} fecha {}: {}", sedeId, fecha, lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Calcula el campo disponible comparando cuposRestantes con 0
    private TurnoDisponibleResponseDTO mapToDTO(TurnoDisponible t) {
        return new TurnoDisponibleResponseDTO(
                t.getIdTurno(), t.getSedeId(), t.getFecha(),
                t.getHoraInicio(), t.getHoraFin(),
                t.getCapacidad(), t.getCuposRestantes(),
                t.getCuposRestantes() > 0
        );
    }
}