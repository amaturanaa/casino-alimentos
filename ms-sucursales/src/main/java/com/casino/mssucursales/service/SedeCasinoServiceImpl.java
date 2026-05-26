package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.SedeCasinoRequestDTO;
import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.model.SedeCasino;
import com.casino.mssucursales.repository.SedeCasinoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad SedeCasino
// Contiene toda la lógica de negocio relacionada a sedes del casino
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class SedeCasinoServiceImpl implements SedeCasinoService {

    // Repositorio JPA inyectado mediante constructor
    private final SedeCasinoRepository sedeCasinoRepository;

    @Override
    public SedeCasinoResponseDTO crearSede(SedeCasinoRequestDTO dto) {
        log.info("Creando sede: {}", dto.getNombreSede());

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        // true como estadoOperativo porque nueva sede siempre operativa
        SedeCasino sede = new SedeCasino(
                null,
                dto.getNombreSede(),
                dto.getDireccion(),
                dto.getCapacidadComensales(),
                dto.getHoraApertura(),
                dto.getHoraCierre(),
                true
        );

        // Persistencia en base de datos mediante JpaRepository
        SedeCasino guardada = sedeCasinoRepository.save(sede);
        log.info("Sede creada con id: {}", guardada.getIdSedeCasino());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToResponse(guardada);
    }

    @Override
    public List<SedeCasinoResponseDTO> listarSedes() {
        log.info("Listando todas las sedes");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<SedeCasinoResponseDTO> lista = new ArrayList<>();
        List<SedeCasino> sedes = sedeCasinoRepository.findAll();

        for (SedeCasino s : sedes) {
            lista.add(mapToResponse(s));
        }

        log.info("Total sedes encontradas: {}", lista.size());
        return lista;
    }

    @Override
    public SedeCasinoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando sede con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        // Este endpoint es consumido por múltiples microservicios via Feign
        SedeCasino sede = sedeCasinoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sede no encontrada con id: {}", id);
                    return new RuntimeException("Sede no encontrada");
                });
        return mapToResponse(sede);
    }

    @Override
    public SedeCasinoResponseDTO cambiarEstado(Long id, Boolean estado) {
        log.info("Cambiando estado operativo de sede {} a {}", id, estado);

        SedeCasino sede = sedeCasinoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sede no encontrada para cambiar estado: {}", id);
                    return new RuntimeException("Sede no encontrada");
                });

        // Actualiza solo el estado operativo — no modifica otros campos
        // Al desactivar, los microservicios via Feign rechazarán crear recursos
        sede.setEstadoOperativo(estado);
        SedeCasino actualizada = sedeCasinoRepository.save(sede);
        log.info("Estado operativo de sede {} actualizado a {}", id, estado);
        return mapToResponse(actualizada);
    }

    @Override
    public List<SedeCasinoResponseDTO> listarPorEstado(Boolean estado) {
        log.info("Listando sedes con estado operativo: {}", estado);

        List<SedeCasinoResponseDTO> lista = new ArrayList<>();
        List<SedeCasino> sedes = sedeCasinoRepository.findByEstadoOperativo(estado);

        for (SedeCasino s : sedes) {
            lista.add(mapToResponse(s));
        }

        log.info("Total sedes con estado {}: {}", estado, lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    private SedeCasinoResponseDTO mapToResponse(SedeCasino sede) {
        return new SedeCasinoResponseDTO(
                sede.getIdSedeCasino(),
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getCapacidadComensales(),
                sede.getHoraApertura(),
                sede.getHoraCierre(),
                sede.getEstadoOperativo()
        );
    }
}