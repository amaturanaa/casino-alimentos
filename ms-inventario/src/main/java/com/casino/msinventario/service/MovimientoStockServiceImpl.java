package com.casino.msinventario.service;

import com.casino.msinventario.dto.MovimientoStockRequestDTO;
import com.casino.msinventario.dto.MovimientoStockResponseDTO;
import com.casino.msinventario.model.Ingrediente;
import com.casino.msinventario.model.MovimientoStock;
import com.casino.msinventario.model.TipoMovimiento;
import com.casino.msinventario.repository.IngredienteRepository;
import com.casino.msinventario.repository.MovimientoStockRepository;
import com.casino.msinventario.repository.TipoMovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad MovimientoStock
// Contiene toda la lógica de negocio relacionada a movimientos de inventario
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientoStockServiceImpl implements MovimientoStockService {

    // Repositorio JPA para acceso a datos de MovimientoStock
    private final MovimientoStockRepository movimientoRepository;

    // Repositorio JPA para acceso a datos de Ingrediente
    // Necesario para actualizar el stock después de cada movimiento
    private final IngredienteRepository ingredienteRepository;

    // Repositorio JPA para acceso a datos de TipoMovimiento
    // Necesario para determinar si el movimiento suma o resta del stock
    private final TipoMovimientoRepository tipoMovimientoRepository;

    @Override
    public MovimientoStockResponseDTO registrar(MovimientoStockRequestDTO dto) {
        log.info("Registrando movimiento para ingrediente: {} tipo: {}",
                dto.getIngredienteId(), dto.getTipoMovimientoId());

        // Verifica que el ingrediente exista antes de registrar el movimiento
        Ingrediente ingrediente = ingredienteRepository.findById(dto.getIngredienteId())
                .orElseThrow(() -> {
                    log.error("Ingrediente no encontrado: {}", dto.getIngredienteId());
                    return new RuntimeException("Ingrediente no encontrado");
                });

        // Verifica que el tipo de movimiento exista
        TipoMovimiento tipo = tipoMovimientoRepository.findById(dto.getTipoMovimientoId())
                .orElseThrow(() -> {
                    log.error("Tipo de movimiento no encontrado: {}", dto.getTipoMovimientoId());
                    return new RuntimeException("Tipo de movimiento no encontrado");
                });

        // Lógica de negocio: SALIDA y MERMA restan stock, ENTRADA suma stock
        // Se usa toUpperCase() para evitar problemas de mayúsculas/minúsculas
        String nombreTipo = tipo.getNombreTipoMovimiento().toUpperCase();
        if (nombreTipo.contains("SALIDA") || nombreTipo.contains("MERMA")) {
            // Validación de negocio: no se puede sacar más de lo que hay en stock
            if (ingrediente.getStockActual() < dto.getCantidad()) {
                log.warn("Stock insuficiente para ingrediente: {} stock: {} cantidad: {}",
                        ingrediente.getIdIngrediente(),
                        ingrediente.getStockActual(),
                        dto.getCantidad());
                throw new RuntimeException("Stock insuficiente");
            }
            // Resta la cantidad al stock actual del ingrediente
            ingrediente.setStockActual(ingrediente.getStockActual() - dto.getCantidad());
            log.info("Stock reducido — nuevo stock: {}", ingrediente.getStockActual());
        } else {
            // Suma la cantidad al stock actual del ingrediente (ENTRADA)
            ingrediente.setStockActual(ingrediente.getStockActual() + dto.getCantidad());
            log.info("Stock aumentado — nuevo stock: {}", ingrediente.getStockActual());
        }

        // Persiste el nuevo stock del ingrediente en la base de datos
        ingredienteRepository.save(ingrediente);

        // Construcción de la entidad MovimientoStock desde el DTO
        // LocalDateTime.now() registra la fecha y hora exacta del movimiento
        MovimientoStock movimiento = new MovimientoStock(
                null,
                ingrediente,
                tipo,
                dto.getCantidad(),
                LocalDateTime.now(),
                dto.getMotivo()
        );

        // Persistencia del movimiento y retorno del DTO con el stock resultante
        MovimientoStock guardado = movimientoRepository.save(movimiento);
        log.info("Movimiento registrado con id: {}", guardado.getIdMovimiento());
        return mapToDTO(guardado, ingrediente.getStockActual());
    }

    @Override
    public List<MovimientoStockResponseDTO> listar() {
        log.info("Listando todos los movimientos de stock");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos = movimientoRepository.findAll();

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        log.info("Total movimientos encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public List<MovimientoStockResponseDTO> listarPorIngrediente(Long ingredienteId) {
        log.info("Listando movimientos para ingrediente: {}", ingredienteId);

        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos = movimientoRepository
                .findByIngrediente_IdIngrediente(ingredienteId);

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        log.info("Total movimientos para ingrediente {}: {}", ingredienteId, lista.size());
        return lista;
    }

    @Override
    public List<MovimientoStockResponseDTO> listarPorTipo(Long tipoMovimientoId) {
        log.info("Listando movimientos por tipo: {}", tipoMovimientoId);

        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos = movimientoRepository
                .findByTipoMovimiento_IdTipoMovimiento(tipoMovimientoId);

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        log.info("Total movimientos de tipo {}: {}", tipoMovimientoId, lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye datos del ingrediente y tipo de movimiento para enriquecer la respuesta
    // stockResultante es el stock del ingrediente después de aplicar el movimiento
    private MovimientoStockResponseDTO mapToDTO(MovimientoStock m, Double stockResultante) {
        return new MovimientoStockResponseDTO(
                m.getIdMovimiento(),
                m.getIngrediente().getIdIngrediente(),
                m.getIngrediente().getNombreIngrediente(),
                m.getTipoMovimiento().getNombreTipoMovimiento(),
                m.getCantidad(),
                m.getFechaMovimiento(),
                m.getMotivo(),
                stockResultante
        );
    }
}