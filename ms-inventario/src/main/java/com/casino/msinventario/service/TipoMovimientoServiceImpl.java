package com.casino.msinventario.service;

import com.casino.msinventario.dto.TipoMovimientoRequestDTO;
import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import com.casino.msinventario.model.TipoMovimiento;
import com.casino.msinventario.repository.TipoMovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad TipoMovimiento
// Contiene toda la lógica de negocio relacionada a tipos de movimiento
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

    // Repositorio JPA inyectado mediante constructor
    private final TipoMovimientoRepository tipoMovimientoRepository;

    @Override
    public TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto) {
        log.info("Creando tipo de movimiento: {}", dto.getNombreTipoMovimiento());

        // Validación de negocio: nombre único en el sistema
        // Evita duplicar tipos como "ENTRADA", "SALIDA" o "MERMA"
        if (tipoMovimientoRepository.existsByNombreTipoMovimiento(dto.getNombreTipoMovimiento())) {
            log.warn("Tipo de movimiento ya existe: {}", dto.getNombreTipoMovimiento());
            throw new RuntimeException("El tipo de movimiento ya existe");
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        TipoMovimiento tipo = new TipoMovimiento(null, dto.getNombreTipoMovimiento());

        // Persistencia en base de datos mediante JpaRepository
        TipoMovimiento guardado = tipoMovimientoRepository.save(tipo);
        log.info("Tipo de movimiento creado con id: {}", guardado.getIdTipoMovimiento());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return new TipoMovimientoResponseDTO(
                guardado.getIdTipoMovimiento(),
                guardado.getNombreTipoMovimiento()
        );
    }

    @Override
    public List<TipoMovimientoResponseDTO> listar() {
        log.info("Listando todos los tipos de movimiento");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<TipoMovimientoResponseDTO> lista = new ArrayList<>();
        List<TipoMovimiento> tipos = tipoMovimientoRepository.findAll();

        for (TipoMovimiento tipo : tipos) {
            lista.add(new TipoMovimientoResponseDTO(
                    tipo.getIdTipoMovimiento(),
                    tipo.getNombreTipoMovimiento()
            ));
        }

        log.info("Total tipos de movimiento encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public TipoMovimientoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando tipo de movimiento con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        TipoMovimiento t = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tipo de movimiento no encontrado con id: {}", id);
                    return new RuntimeException("Tipo de movimiento no encontrado");
                });

        return new TipoMovimientoResponseDTO(
                t.getIdTipoMovimiento(),
                t.getNombreTipoMovimiento()
        );
    }
}