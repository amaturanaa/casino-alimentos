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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoStockServiceImpl implements MovimientoStockService {

    private final MovimientoStockRepository movimientoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final TipoMovimientoRepository tipoMovimientoRepository;

    @Override
    public MovimientoStockResponseDTO registrar(MovimientoStockRequestDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(dto.getIngredienteId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        TipoMovimiento tipo = tipoMovimientoRepository.findById(dto.getTipoMovimientoId())
                .orElseThrow(() -> new RuntimeException("TipoMovimiento no encontrado"));

        String nombreTipo = tipo.getNombreTipoMovimiento().toUpperCase();
        if (nombreTipo.contains("SALIDA")) {
            if (ingrediente.getStockActual() < dto.getCantidad())
                throw new RuntimeException("Stock insuficiente");
            ingrediente.setStockActual(ingrediente.getStockActual() - dto.getCantidad());
        } else {
            ingrediente.setStockActual(ingrediente.getStockActual() + dto.getCantidad());
        }

        ingredienteRepository.save(ingrediente);

        MovimientoStock movimiento = new MovimientoStock(
                null,
                ingrediente,
                tipo,
                dto.getCantidad(),
                LocalDateTime.now(),
                dto.getMotivo()
        );

        return mapToDTO(movimientoRepository.save(movimiento), ingrediente.getStockActual());
    }

    @Override
    public List<MovimientoStockResponseDTO> listar() {

        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos = movimientoRepository.findAll();

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        return lista;
    }

    @Override
    public List<MovimientoStockResponseDTO> listarPorIngrediente(Long ingredienteId) {

        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos = movimientoRepository.findByIngrediente_IdIngrediente(ingredienteId);

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        return lista;
    }

    @Override
    public List<MovimientoStockResponseDTO> listarPorTipo(Long tipoMovimientoId) {

        List<MovimientoStockResponseDTO> lista = new ArrayList<>();
        List<MovimientoStock> movimientos =  movimientoRepository.findByTipoMovimiento_IdTipoMovimiento(tipoMovimientoId);

        for (MovimientoStock movimiento : movimientos) {
            lista.add(mapToDTO(movimiento, movimiento.getIngrediente().getStockActual()));
        }

        return lista;
    }

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
