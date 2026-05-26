package com.casino.msmenu.service;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.model.Plato;
import com.casino.msmenu.model.ProgramacionDiaria;
import com.casino.msmenu.repository.PlatoRepository;
import com.casino.msmenu.repository.ProgramacionDiariaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad ProgramacionDiaria
// Contiene toda la lógica de negocio relacionada a la programación del menú
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramacionDiariaServiceImpl implements ProgramacionDiariaService {

    // Repositorio JPA para acceso a datos de ProgramacionDiaria
    private final ProgramacionDiariaRepository programacionRepository;

    // Repositorio JPA para acceso a datos de Plato
    // Necesario para verificar que el plato exista antes de programarlo
    private final PlatoRepository platoRepository;

    @Override
    public ProgramacionDiariaResponseDTO crear(ProgramacionDiariaRequestDTO dto) {
        log.info("Creando programación para plato: {} sede: {} fecha: {}",
                dto.getPlatoId(), dto.getSedeId(), dto.getFecha());

        // Validación de negocio: no se puede programar para una fecha pasada
        if (dto.getFecha().isBefore(LocalDate.now())) {
            log.warn("Fecha inválida para programación: {}", dto.getFecha());
            throw new IllegalArgumentException("La fecha no puede ser anterior a hoy");
        }

        // Validación de negocio: debe haber al menos una ración disponible
        if (dto.getRacionesDisponibles() <= 0) {
            log.warn("Raciones inválidas: {}", dto.getRacionesDisponibles());
            throw new IllegalArgumentException("Las raciones deben ser mayores a cero");
        }

        // Verifica que el plato exista en la base de datos local
        Plato plato = platoRepository.findById(dto.getPlatoId())
                .orElseThrow(() -> {
                    log.error("Plato no encontrado: {}", dto.getPlatoId());
                    return new IllegalArgumentException("Plato no encontrado");
                });

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        ProgramacionDiaria prog = new ProgramacionDiaria(
                null,
                dto.getFecha(),
                dto.getSedeId(),
                plato,
                dto.getRacionesDisponibles()
        );

        // Persistencia en base de datos mediante JpaRepository
        ProgramacionDiaria guardada = programacionRepository.save(prog);
        log.info("Programación creada con id: {}", guardada.getIdProgramacion());
        return mapToDTO(guardada);
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorFecha(LocalDate fecha) {
        log.info("Listando programaciones para fecha: {}", fecha);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<ProgramacionDiariaResponseDTO> lista = new ArrayList<>();
        List<ProgramacionDiaria> programaciones = programacionRepository.findByFecha(fecha);

        for (ProgramacionDiaria prog : programaciones) {
            lista.add(mapToDTO(prog));
        }

        log.info("Total programaciones para fecha {}: {}", fecha, lista.size());
        return lista;
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando programaciones para sede: {}", sedeId);

        List<ProgramacionDiariaResponseDTO> lista = new ArrayList<>();
        List<ProgramacionDiaria> programaciones = programacionRepository.findBySedeId(sedeId);

        for (ProgramacionDiaria prog : programaciones) {
            lista.add(mapToDTO(prog));
        }

        log.info("Total programaciones para sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public List<ProgramacionDiariaResponseDTO> listarPorFechaYSede(LocalDate fecha, Long sedeId) {
        log.info("Listando programaciones para fecha: {} sede: {}", fecha, sedeId);

        List<ProgramacionDiariaResponseDTO> lista = new ArrayList<>();
        List<ProgramacionDiaria> programaciones = programacionRepository
                .findByFechaAndSedeId(fecha, sedeId);

        for (ProgramacionDiaria prog : programaciones) {
            lista.add(mapToDTO(prog));
        }

        log.info("Total programaciones para fecha {} sede {}: {}", fecha, sedeId, lista.size());
        return lista;
    }

    // @Transactional garantiza que el descuento de ración sea atómico
    // Si ocurre un error durante la operación, la transacción se revierte
    // Evita condiciones de carrera al descontar raciones simultáneamente
    @Override
    @Transactional
    public ProgramacionDiariaResponseDTO descontarRacion(Long id) {
        log.info("Descontando ración de programación: {}", id);

        ProgramacionDiaria prog = programacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Programación no encontrada: {}", id);
                    return new IllegalArgumentException("Programación no encontrada");
                });

        // Validación de negocio: no se puede descontar si no hay raciones
        if (prog.getRacionesDisponibles() <= 0) {
            log.warn("Sin raciones disponibles para programación: {}", id);
            throw new IllegalStateException("No hay raciones disponibles");
        }

        // Descuenta una ración del total disponible
        prog.setRacionesDisponibles(prog.getRacionesDisponibles() - 1);
        log.info("Raciones restantes en programación {}: {}", id, prog.getRacionesDisponibles());

        return mapToDTO(prog);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye el nombre del plato para enriquecer la respuesta
    private ProgramacionDiariaResponseDTO mapToDTO(ProgramacionDiaria p) {
        return new ProgramacionDiariaResponseDTO(
                p.getIdProgramacion(),
                p.getFecha(),
                p.getSedeId(),
                p.getPlato().getIdPlato(),
                p.getPlato().getNombrePlato(),
                p.getRacionesDisponibles()
        );
    }
}